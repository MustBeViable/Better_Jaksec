package com.api.lesson;

import com.api.common.Language;
import com.api.common.LanguageRepository;
import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import com.api.login.Auth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;
    private LessonMapper lessonMapper;
    private LanguageRepository languageRepository;
    private LessonService service;
    private Auth auth;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        courseRepository = mock(CourseRepository.class);
        lessonMapper = mock(LessonMapper.class);
        languageRepository = mock(LanguageRepository.class);
        auth = mock(Auth.class);

        service = new LessonService(lessonRepository, courseRepository, languageRepository, lessonMapper);
    }

    @Test
    @DisplayName("create() allows admin and saves lesson")
    void create_savesEntity_returnsDto() {

        when(auth.getRole()).thenReturn("admin");

        CreateLessonRequest request = new CreateLessonRequest();
        request.setLessonName("Test lesson");
        request.setDate(Instant.parse("2026-02-06T08:00:00Z"));
        request.setCourseId(10L);
        request.setLocale("en");

        Language language = new Language();
        language.setLocale("en");

        Lesson lessonEntity = new Lesson("Test lesson", request.getDate());
        Course course = new Course();
        course.setCourseID(10L);

        Lesson savedLesson = new Lesson("Test lesson", request.getDate());
        savedLesson.setLessonID(1L);
        savedLesson.setCourse(course);
        savedLesson.setLanguage(language);

        LessonDto dto = new LessonDto(1L, "Test lesson", "en_US", request.getDate(), 10L);

        when(languageRepository.findById("en")).thenReturn(Optional.of(language));
        when(lessonMapper.toLessonEntity(request)).thenReturn(lessonEntity);
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(lessonRepository.save(lessonEntity)).thenReturn(savedLesson);
        when(lessonMapper.toLessonDto(savedLesson)).thenReturn(dto);

        LessonDto result = service.create(request, auth);

        assertEquals(1L, result.getLessonID());
        assertEquals(10L, result.getCourseId());

        verify(languageRepository).findById("en");
        verify(lessonRepository).save(lessonEntity);
    }

    @Test
    @DisplayName("create() throws if not admin or teacher")
    void create_throwsIfUnauthorized() {

        when(auth.getRole()).thenReturn("student");

        CreateLessonRequest request = new CreateLessonRequest();

        assertThrows(UnauthorizedException.class,
                () -> service.create(request, auth));
    }

    @Test
    @DisplayName("create() throws if course missing")
    void create_throwsIfCourseMissing() {

        when(auth.getRole()).thenReturn("admin");

        CreateLessonRequest request = new CreateLessonRequest();
        request.setLessonName("Test lesson");
        request.setDate(Instant.parse("2026-02-06T08:00:00Z"));
        request.setLocale("en");
        request.setCourseId(null);

        Language language = new Language();
        language.setLocale("en");

        Lesson lessonEntity = new Lesson("Test lesson", request.getDate());

        when(languageRepository.findById("en")).thenReturn(Optional.of(language));
        when(lessonMapper.toLessonEntity(request)).thenReturn(lessonEntity);

        assertThrows(BadRequestException.class,
                () -> service.create(request, auth));
    }

    @Test
    @DisplayName("read() allows admin")
    void read_allowsAdmin() {

        when(auth.getRole()).thenReturn("admin");
        when(lessonRepository.existsById(5L)).thenReturn(true);

        Lesson lesson = new Lesson("Name", Instant.now());
        lesson.setLessonID(5L);

        LessonDto dto = new LessonDto(5L, "Name", "en_US", lesson.getDate(), null);

        when(lessonRepository.getReferenceById(5L)).thenReturn(lesson);
        when(lessonMapper.toLessonDto(lesson)).thenReturn(dto);

        LessonDto result = service.read(5L, auth);

        assertEquals(5L, result.getLessonID());
    }

    @Test
    @DisplayName("read() allows enrolled student")
    void read_allowsEnrolledStudent() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("student@test.com");
        when(lessonRepository.existsById(5L)).thenReturn(true);
        when(lessonRepository.isStudentEnrolledInLesson("student@test.com", 5L))
                .thenReturn(true);

        Lesson lesson = new Lesson("Name", Instant.now());
        lesson.setLessonID(5L);

        LessonDto dto = new LessonDto(5L, "Name", "en_US", lesson.getDate(), null);

        when(lessonRepository.getReferenceById(5L)).thenReturn(lesson);
        when(lessonMapper.toLessonDto(lesson)).thenReturn(dto);

        LessonDto result = service.read(5L, auth);

        assertEquals(5L, result.getLessonID());
    }

    @Test
    @DisplayName("read() throws if not found")
    void read_throwsIfNotExists() {

        when(lessonRepository.existsById(99L)).thenReturn(false);

        assertThrows(BadRequestException.class,
                () -> service.read(99L, auth));
    }

    @Test
    @DisplayName("read() throws if student not enrolled")
    void read_throwsIfNotEnrolled() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("student@test.com");
        when(lessonRepository.existsById(5L)).thenReturn(true);
        when(lessonRepository.isStudentEnrolledInLesson("student@test.com", 5L))
                .thenReturn(false);

        assertThrows(UnauthorizedException.class,
                () -> service.read(5L, auth));
    }

    @Test
    @DisplayName("update() allows teacher")
    void update_allowsTeacher() {

        when(auth.getRole()).thenReturn("teacher");

        Lesson lesson = new Lesson("Old", Instant.now());
        lesson.setLessonID(5L);

        Course course = new Course();
        course.setCourseID(10L);

        UpdateLessonRequest request = new UpdateLessonRequest();
        request.setCourseId(10L);

        when(lessonRepository.getReferenceById(5L)).thenReturn(lesson);
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(lessonMapper.toLessonDto(lesson))
                .thenReturn(new LessonDto(5L, "Old", "en_US", lesson.getDate(), 10L));

        LessonDto result = service.update(5L, request, auth);

        verify(lessonMapper).updateLessonEntity(lesson, request);
        verify(lessonRepository).save(lesson);
        assertEquals(10L, lesson.getCourse().getCourseID());
        assertEquals(5L, result.getLessonID());
    }

    @Test
    @DisplayName("update() throws if unauthorized")
    void update_throwsIfUnauthorized() {

        when(auth.getRole()).thenReturn("student");

        UpdateLessonRequest request = new UpdateLessonRequest();

        assertThrows(UnauthorizedException.class,
                () -> service.update(5L, request, auth));
    }

    @Test
    @DisplayName("delete() allows admin")
    void delete_allowsAdmin() {

        when(auth.getRole()).thenReturn("admin");

        Lesson lesson = new Lesson("Name", Instant.now());

        when(lessonRepository.findById(5L)).thenReturn(Optional.of(lesson));

        service.delete(5L, auth);

        verify(lessonRepository).delete(lesson);
    }

    @Test
    @DisplayName("delete() throws if unauthorized")
    void delete_throwsIfUnauthorized() {

        when(auth.getRole()).thenReturn("student");

        assertThrows(UnauthorizedException.class,
                () -> service.delete(5L, auth));
    }

    @Test
    @DisplayName("delete() throws if not found")
    void delete_throwsIfNotFound() {

        when(auth.getRole()).thenReturn("admin");
        when(lessonRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.delete(5L, auth));
    }
}