package com.api.course;

import com.api.assignment.Assignment;
import com.api.assignment.AssignmentRepository;
import com.api.common.LanguageRepository;
import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.Lesson;
import com.api.lesson.LessonRepository;
import com.api.login.Auth;
import com.api.teacher.Teacher;
import com.api.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;
    private TeacherRepository teacherRepository;
    private AssignmentRepository assignmentRepository;
    private LanguageRepository languageRespository;
    private CourseMapper mapper;
    private CourseService service;
    private Auth auth;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        lessonRepository = mock(LessonRepository.class);
        teacherRepository = mock(TeacherRepository.class);
        assignmentRepository = mock(AssignmentRepository.class);
        languageRespository = mock(LanguageRepository.class);
        mapper = mock(CourseMapper.class);
        auth = mock(Auth.class);

        service = new CourseService(
                courseRepository,
                lessonRepository,
                teacherRepository,
                assignmentRepository,
                languageRespository,
                mapper
        );
    }

    @Test
    @DisplayName("create() sets all relations and returns DTO")
    void create_returnsDtoWithRelations() {

        when(auth.getRole()).thenReturn("admin");

        CreateCourseRequest request = new CreateCourseRequest();
        request.setCourseName("Math 101");
        request.setLessonIds(Set.of(1L, 2L));
        request.setAssignmentIds(Set.of(10L));
        request.setTeacherIds(Set.of(100));

        Course courseEntity = new Course();
        when(mapper.toEmptyCourseEntity(request)).thenReturn(courseEntity);

        Lesson l1 = new Lesson(); l1.setLessonID(1L);
        Lesson l2 = new Lesson(); l2.setLessonID(2L);
        when(lessonRepository.findAllById(request.getLessonIds()))
                .thenReturn(List.of(l1, l2));

        Assignment a1 = new Assignment(); a1.setAssignmentID(10L);
        when(assignmentRepository.findAllById(request.getAssignmentIds()))
                .thenReturn(List.of(a1));

        Teacher t1 = new Teacher();
        t1.setTeacherID(100);
        t1.setFirstName("John");
        t1.setLastName("Doe");
        t1.setEmail("john@school.com");
        t1.setCourses(new HashSet<>());
        when(teacherRepository.findAllById(request.getTeacherIds()))
                .thenReturn(List.of(t1));

        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);

        CourseDto dto = new CourseDto(
                1L,
                "Math 101",
                "en_US",
                Set.of(1L, 2L),
                Set.of(10L),
                Set.of("John Doe")
        );

        when(mapper.toCourseDto(courseEntity)).thenReturn(dto);

        CourseDto result = service.create(request, auth);

        assertEquals("Math 101", result.getName());
        assertEquals(Set.of(1L, 2L), result.getLessonIds());
        assertEquals(Set.of(10L), result.getAssignmentIds());
        assertEquals(Set.of("John Doe"), result.getTeacherNames());

        verify(courseRepository).save(courseEntity);
    }

    @Test
    @DisplayName("read() returns DTO when admin")
    void read_returnsDtoWhenAdmin() {

        when(auth.getRole()).thenReturn("admin");
        when(auth.getEmail()).thenReturn("admin@school.com");

        Course course = new Course();
        course.setCourseID(1L);
        course.setTeachers(new HashSet<>());
        course.setGrades(new HashSet<>());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseDto dto = new CourseDto(
                1L,
                "Physics 101",
                "en_US",
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );

        when(mapper.toCourseDto(course)).thenReturn(dto);

        CourseDto res = service.read(1L, auth);

        assertEquals("Physics 101", res.getName());
    }

    @Test
    @DisplayName("read() throws when course does not exist")
    void read_throwsIfNotExists() {

        when(auth.getRole()).thenReturn("admin");

        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.read(99L, auth));
    }

    @Test
    @DisplayName("update() updates relations and returns DTO")
    void updateCourse_returnsDtoWithUpdatedRelations() {

        when(auth.getRole()).thenReturn("teacher");
        when(auth.getEmail()).thenReturn("teacher@school.com");

        Course course = new Course();
        course.setCourseID(1L);
        course.setLessons(new HashSet<>());
        course.setAssignments(new HashSet<>());

        Teacher existingTeacher = new Teacher();
        existingTeacher.setEmail("teacher@school.com");
        existingTeacher.setCourses(new HashSet<>(Set.of(course)));

        course.setTeachers(new HashSet<>(Set.of(existingTeacher)));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        UpdateCourseRequest request = new UpdateCourseRequest();
        request.setLessonIds(Set.of(2L));
        request.setAssignmentIds(Set.of(20L));
        request.setTeacherIds(Set.of(200));

        Lesson newLesson = new Lesson();
        newLesson.setLessonID(2L);
        when(lessonRepository.findAllById(request.getLessonIds()))
                .thenReturn(List.of(newLesson));

        Assignment newAssignment = new Assignment();
        newAssignment.setAssignmentID(20L);
        when(assignmentRepository.findAllById(request.getAssignmentIds()))
                .thenReturn(List.of(newAssignment));

        Teacher newTeacher = new Teacher();
        newTeacher.setTeacherID(200);
        newTeacher.setCourses(new HashSet<>());
        when(teacherRepository.findAllById(request.getTeacherIds()))
                .thenReturn(List.of(newTeacher));

        CourseDto dto = new CourseDto(
                1L,
                "Updated Course",
                "en_US",
                Set.of(2L),
                Set.of(20L),
                Set.of()
        );

        when(mapper.toCourseDto(course)).thenReturn(dto);

        CourseDto updated = service.update(1L, request, auth);

        assertEquals(Set.of(2L), updated.getLessonIds());
        assertEquals(Set.of(20L), updated.getAssignmentIds());
    }

    @Test
    @DisplayName("update() throws if unauthorized")
    void update_throwsIfUnauthorized() {

        when(auth.getRole()).thenReturn("student");

        UpdateCourseRequest request = new UpdateCourseRequest();

        assertThrows(UnauthorizedException.class,
                () -> service.update(1L, request, auth));
    }

    @Test
    @DisplayName("delete() deletes course when teacher belongs to course")
    void delete_removesCourseWhenExists() {

        when(auth.getRole()).thenReturn("teacher");
        when(auth.getEmail()).thenReturn("teacher@school.com");

        Course course = new Course();
        course.setCourseID(1L);

        Teacher teacher = new Teacher();
        teacher.setEmail("teacher@school.com");

        course.setTeachers(Set.of(teacher));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> service.delete(1L, auth));

        verify(courseRepository).deleteById(1L);
    }

    @Test
    @DisplayName("delete() throws if course not found")
    void delete_throwsIfNotExists() {

        when(auth.getRole()).thenReturn("admin");

        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.delete(99L, auth));
    }
}