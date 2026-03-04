package com.api.jointable.student_course;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import com.api.jointable.student_course.dto.CreateStudentCourse;
import com.api.jointable.student_course.dto.DeleteFromCourseRequest;
import com.api.jointable.student_course.dto.StudentCourseDto;
import com.api.jointable.student_course.dto.UpdateStudentCourse;
import com.api.login.Auth;
import com.api.student.Student;
import com.api.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentCourseServiceTest {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private StudentCourseRepository studentCourseRepository;
    private StudentCourseMapper mapper;
    private StudentCourseService service;
    private Auth auth;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        courseRepository = mock(CourseRepository.class);
        studentCourseRepository = mock(StudentCourseRepository.class);
        mapper = mock(StudentCourseMapper.class);
        auth = mock(Auth.class);

        service = new StudentCourseService(
                studentRepository,
                courseRepository,
                studentCourseRepository,
                mapper
        );
    }

    @Test
    @DisplayName("create() throws if not admin/teacher")
    void create_throwsIfNotTeacherOrAdmin() {

        when(auth.getRole()).thenReturn("student");

        CreateStudentCourse request = new CreateStudentCourse();
        request.setStudentId(1);
        request.setCourseId(2L);
        request.setGrade(5);

        assertThrows(UnauthorizedException.class,
                () -> service.create(request, auth));

        verify(studentCourseRepository, never()).save(any());
    }

    @Test
    @DisplayName("create() throws if invalid student id")
    void create_throwsIfInvalidStudent() {

        when(auth.getRole()).thenReturn("teacher");

        CreateStudentCourse request = new CreateStudentCourse();
        request.setStudentId(123);
        request.setCourseId(2L);
        request.setGrade(5);

        when(mapper.toEntity(request)).thenReturn(new StudentCourse());
        when(studentRepository.findById(123)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.create(request, auth));

        verify(studentCourseRepository, never()).save(any());
    }

    @Test
    @DisplayName("create() throws if invalid course id")
    void create_throwsIfInvalidCourse() {

        when(auth.getRole()).thenReturn("admin");

        CreateStudentCourse request = new CreateStudentCourse();
        request.setStudentId(1);
        request.setCourseId(999L);
        request.setGrade(5);

        StudentCourse entity = new StudentCourse();
        when(mapper.toEntity(request)).thenReturn(entity);

        when(studentRepository.findById(1)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.create(request, auth));

        verify(studentCourseRepository, never()).save(any());
    }

    @Test
    @DisplayName("create() saves and returns DTO")
    void create_savesAndReturnsDto() {

        when(auth.getRole()).thenReturn("teacher");

        CreateStudentCourse request = new CreateStudentCourse();
        request.setStudentId(1);
        request.setCourseId(2L);
        request.setGrade(4);

        Student student = new Student();
        student.setStudentID(1);
        student.setEmail("s1@test.fi");

        Course course = new Course();
        course.setCourseID(2L);

        StudentCourse entity = new StudentCourse();

        StudentCourseDto dto = new StudentCourseDto(10L, 1, 2L, 4);

        when(mapper.toEntity(request)).thenReturn(entity);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(mapper.toDto(entity)).thenReturn(dto);

        StudentCourseDto res = service.create(request, auth);

        assertAll(
                () -> assertEquals(10L, res.getGradeId()),
                () -> assertEquals(1, res.getStudentId()),
                () -> assertEquals(2L, res.getCourseId()),
                () -> assertEquals(4, res.getGrade())
        );

        verify(studentCourseRepository).save(entity);
    }

    @Test
    @DisplayName("readStudentIdsByCourse() throws if invalid course id")
    void readStudentIdsByCourse_throwsIfInvalidCourse() {

        when(courseRepository.existsById(999L)).thenReturn(false);

        assertThrows(BadRequestException.class,
                () -> service.readStudentIdsByCourse(999L));
    }

    @Test
    @DisplayName("readStudentIdsByCourse() returns student ids")
    void readStudentIdsByCourse_returnsIds() {

        when(courseRepository.existsById(2L)).thenReturn(true);

        Student s1 = new Student(); s1.setStudentID(1);
        Student s2 = new Student(); s2.setStudentID(2);

        StudentCourse sc1 = new StudentCourse(); sc1.setStudent(s1);
        StudentCourse sc2 = new StudentCourse(); sc2.setStudent(s2);

        when(studentCourseRepository.findAllByCourse_CourseID(2L))
                .thenReturn(List.of(sc1, sc2));

        List<Integer> res = service.readStudentIdsByCourse(2L);

        assertEquals(List.of(1, 2), res);
    }

    @Test
    @DisplayName("read() throws if grade not found")
    void read_throwsIfNotFound() {

        when(studentCourseRepository.findById(55L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.read(55L, auth));
    }

    @Test
    @DisplayName("read() allows admin/teacher")
    void read_allowsAdminTeacher() {

        when(auth.getRole()).thenReturn("admin");

        Student student = new Student();
        student.setEmail("x@test.fi");
        student.setStudentID(1);

        StudentCourse entity = new StudentCourse();
        entity.setStudent(student);

        when(studentCourseRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(new StudentCourseDto());

        assertDoesNotThrow(() -> service.read(1L, auth));
    }

    @Test
    @DisplayName("read() allows self student")
    void read_allowsSelfStudent() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("self@test.fi");

        Student student = new Student();
        student.setEmail("self@test.fi");
        student.setStudentID(1);

        StudentCourse entity = new StudentCourse();
        entity.setStudent(student);

        when(studentCourseRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(new StudentCourseDto());

        assertDoesNotThrow(() -> service.read(1L, auth));
    }

    @Test
    @DisplayName("read() throws if student tries to read someone else")
    void read_throwsIfUnauthorizedStudent() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("wrong@test.fi");

        Student student = new Student();
        student.setEmail("real@test.fi");

        StudentCourse entity = new StudentCourse();
        entity.setStudent(student);

        when(studentCourseRepository.findById(1L)).thenReturn(Optional.of(entity));

        assertThrows(UnauthorizedException.class,
                () -> service.read(1L, auth));
    }

    @Test
    @DisplayName("update() throws if not admin/teacher")
    void update_throwsIfNotTeacherOrAdmin() {

        when(auth.getRole()).thenReturn("student");

        UpdateStudentCourse request = new UpdateStudentCourse();
        request.setGrade(5);
        request.setCourseId(1L);

        assertThrows(UnauthorizedException.class,
                () -> service.update(1L, request, auth));
    }

    @Test
    @DisplayName("update() throws if grade not found")
    void update_throwsIfNotFound() {

        when(auth.getRole()).thenReturn("teacher");
        when(studentCourseRepository.findById(99L)).thenReturn(Optional.empty());

        UpdateStudentCourse request = new UpdateStudentCourse();
        request.setGrade(2);
        request.setCourseId(1L);

        assertThrows(BadRequestException.class,
                () -> service.update(99L, request, auth));
    }

    @Test
    @DisplayName("update() updates and returns DTO")
    void update_success() {

        when(auth.getRole()).thenReturn("admin");

        StudentCourse entity = new StudentCourse();

        when(studentCourseRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(studentCourseRepository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(new StudentCourseDto());

        UpdateStudentCourse request = new UpdateStudentCourse();
        request.setCourseId(1L);
        request.setGrade(5);

        assertDoesNotThrow(() -> service.update(1L, request, auth));

        verify(mapper).updateEntity(entity, request);
        verify(studentCourseRepository).save(entity);
    }

    @Test
    @DisplayName("delete() throws if not admin/teacher")
    void delete_throwsIfNotTeacherOrAdmin() {

        when(auth.getRole()).thenReturn("student");

        DeleteFromCourseRequest request =
                new DeleteFromCourseRequest(1, 2L);

        assertThrows(UnauthorizedException.class,
                () -> service.delete(request, auth));

        verify(studentCourseRepository, never())
                .deleteFromCourse(anyInt(), anyLong());
    }

    @Test
    @DisplayName("delete() deletes from course")
    void delete_success() {

        when(auth.getRole()).thenReturn("teacher");

        DeleteFromCourseRequest request =
                new DeleteFromCourseRequest(1, 2L);

        service.delete(request, auth);

        verify(studentCourseRepository)
                .deleteFromCourse(1, 2L);
    }
}