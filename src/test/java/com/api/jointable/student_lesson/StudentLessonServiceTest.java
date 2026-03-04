package com.api.jointable.student_lesson;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.lesson.Lesson;
import com.api.lesson.LessonRepository;
import com.api.login.Auth;
import com.api.student.Student;
import com.api.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentLessonServiceTest {

    private StudentRepository studentRepository;
    private LessonRepository lessonRepository;
    private StudentLessonRepository studentLessonRepository;
    private StudentLessonMapper mapper;
    private StudentLessonService service;
    private Auth auth;

    @BeforeEach
    void setUp() {

        studentRepository = mock(StudentRepository.class);
        lessonRepository = mock(LessonRepository.class);
        studentLessonRepository = mock(StudentLessonRepository.class);
        mapper = mock(StudentLessonMapper.class);
        auth = mock(Auth.class);

        service = new StudentLessonService(
                studentRepository,
                lessonRepository,
                studentLessonRepository,
                mapper
        );
    }

    @Test
    @DisplayName("create() saves attendance")
    void create_success() {

        CreateStudentLesson request = new CreateStudentLesson();
        request.setStudentId(1);
        request.setLessonId(2L);
        request.setPresent(true);

        Student student = new Student();
        student.setEmail("test@test.com");

        Lesson lesson = new Lesson();
        lesson.setLessonID(2L);

        StudentLesson entity = new StudentLesson();

        StudentLessonDto dto =
                new StudentLessonDto(true, 2L, 1, Instant.now());

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(lessonRepository.findById(2L)).thenReturn(Optional.of(lesson));
        when(mapper.toEntity(request)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        when(auth.getRole()).thenReturn("admin");

        StudentLessonDto result = service.create(request, auth);

        assertTrue(result.getPresent());
        verify(studentLessonRepository).save(entity);
    }

    @Test
    @DisplayName("read() throws if attendance not found")
    void read_notFound() {

        when(studentLessonRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.read(99L, auth));
    }

    @Test
    @DisplayName("update() throws if not teacher or admin")
    void update_unauthorized() {

        when(auth.getRole()).thenReturn("student");

        UpdateStudentLesson request = new UpdateStudentLesson();

        assertThrows(UnauthorizedException.class,
                () -> service.update(1L, request, auth));
    }

    @Test
    @DisplayName("update() works for teacher")
    void update_success() {

        when(auth.getRole()).thenReturn("teacher");

        StudentLesson entity = new StudentLesson();

        when(studentLessonRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(studentLessonRepository.save(entity)).thenReturn(entity);

        when(mapper.toDto(entity))
                .thenReturn(new StudentLessonDto());

        UpdateStudentLesson request = new UpdateStudentLesson();

        assertDoesNotThrow(() ->
                service.update(1L, request, auth));

        verify(mapper).updateEntity(entity, request);
        verify(studentLessonRepository).save(entity);
    }

    @Test
    @DisplayName("delete() throws if not admin or teacher")
    void delete_unauthorized() {

        when(auth.getRole()).thenReturn("student");

        assertThrows(UnauthorizedException.class,
                () -> service.delete(1L, auth));
    }

    @Test
    @DisplayName("delete() deletes attendance")
    void delete_success() {

        when(auth.getRole()).thenReturn("admin");

        service.delete(5L, auth);

        verify(studentLessonRepository).deleteById(5L);
    }
}