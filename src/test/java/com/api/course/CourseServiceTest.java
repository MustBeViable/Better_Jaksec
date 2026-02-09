package com.api.course;

import com.api.assignment.Assignment;
import com.api.assignment.AssignmentRepository;
import com.api.common.error.BadRequestException;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.Lesson;
import com.api.lesson.LessonRepository;
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
    private AssignmentRepository assignmentRespository;
    private CourseMapper mapper;
    private CourseService service;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        lessonRepository = mock(LessonRepository.class);
        teacherRepository = mock(TeacherRepository.class);
        assignmentRespository = mock(AssignmentRepository.class);
        mapper = mock(CourseMapper.class);
        service = new CourseService(
                courseRepository,
                lessonRepository,
                teacherRepository,
                assignmentRespository,
                mapper
        );
    }

    @Test
    @DisplayName("create() sets all relations and returns DTO")
    void create_returnsDtoWithRelations() {
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
        when(assignmentRespository.findAllById(request.getAssignmentIds()))
                .thenReturn(List.of(a1));

        Teacher t1 = new Teacher();
        t1.setTeacherID(100);
        t1.setFirstName("John");
        t1.setLastName("Doe");
        when(teacherRepository.findAllById(request.getTeacherIds()))
                .thenReturn(List.of(t1));

        CourseDto dto = new CourseDto(
                1L,
                "Math 101",
                Set.of(1L, 2L),
                Set.of(10L),
                Set.of("John Doe")
        );

        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);
        when(mapper.toCourseDto(courseEntity)).thenReturn(dto);

        CourseDto result = service.create(request);

        assertAll(
                () -> assertEquals("Math 101", result.getName()),
                () -> assertEquals(Set.of(1L, 2L), result.getLessonIds()),
                () -> assertEquals(Set.of(10L), result.getAssignmentIds()),
                () -> assertEquals(Set.of("John Doe"), result.getTeacherNames())
        );

        verify(courseRepository).save(courseEntity);
    }

    @Test
    @DisplayName("read() returns DTO when course exists")
    void read_returnsDtoWhenExists() {
        Course course = new Course();
        course.setCourseID(1L);

        CourseDto dto = new CourseDto(
                1L,
                "Physics 101",
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(mapper.toCourseDto(course)).thenReturn(dto);

        CourseDto res = service.read(1L);

        assertEquals(1L, res.getId());
        assertEquals("Physics 101", res.getName());
    }

    @Test
    @DisplayName("read() throws BadRequestException when course does not exist")
    void read_throwsIfNotExists() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> service.read(99L));
    }

    @Test
    @DisplayName("updateCourse() updates lessons, assignments, teachers and returns DTO")
    void updateCourse_returnsDtoWithUpdatedRelations() {
        Course course = new Course();
        course.setCourseID(1L);

        Lesson oldLesson = new Lesson();
        oldLesson.setLessonID(1L);
        oldLesson.setCourse(course);
        course.setLessons(new HashSet<>(Set.of(oldLesson)));

        Assignment oldAssignment = new Assignment();
        oldAssignment.setAssignmentID(10L);
        oldAssignment.setCourse(course);
        course.setAssignments(new HashSet<>(Set.of(oldAssignment)));

        Teacher oldTeacher = new Teacher();
        oldTeacher.setTeacherID(100);
        oldTeacher.setFirstName("Old");
        oldTeacher.setLastName("Teacher");
        oldTeacher.getCourses().add(course);
        course.setTeachers(new HashSet<>(Set.of(oldTeacher)));

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
        when(assignmentRespository.findAllById(request.getAssignmentIds()))
                .thenReturn(List.of(newAssignment));

        Teacher newTeacher = new Teacher();
        newTeacher.setTeacherID(200);
        newTeacher.setFirstName("New");
        newTeacher.setLastName("Teacher");
        when(teacherRepository.findAllById(request.getTeacherIds()))
                .thenReturn(List.of(newTeacher));

        CourseDto dto = new CourseDto(
                1L,
                "Updated Course",
                Set.of(2L),
                Set.of(20L),
                Set.of("New Teacher")
        );
        when(mapper.toCourseDto(course)).thenReturn(dto);

        CourseDto updated = service.update(1L, request);

        assertAll(
                () -> assertEquals(Set.of(2L), updated.getLessonIds()),
                () -> assertEquals(Set.of(20L), updated.getAssignmentIds()),
                () -> assertEquals(Set.of("New Teacher"), updated.getTeacherNames())
        );

        verify(mapper).updateCourseEntity(course, request);
    }

    @Test
    @DisplayName("delete() deletes course when it exists")
    void delete_removesCourseWhenExists() {
        Course course = new Course();
        course.setCourseID(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> service.delete(1L));
        verify(courseRepository).deleteById(1L);
    }

    @Test
    @DisplayName("delete() throws BadRequestException when course does not exist")
    void delete_throwsIfNotExists() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> service.delete(99L));
        verify(courseRepository, never()).deleteById(anyLong());
    }
}