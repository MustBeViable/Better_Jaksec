package com.api.lesson;

import com.api.common.error.BadRequestException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;
    private LessonMapper lessonMapper;
    private LessonService service;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        courseRepository = mock(CourseRepository.class);
        lessonMapper = mock(LessonMapper.class);
        service = new LessonService(lessonRepository, courseRepository, lessonMapper);
    }

    @Test
    @DisplayName("Successful creation")
    void create_savesEntity_returnsDto() {
        CreateLessonRequest request = new CreateLessonRequest();
        request.setLessonName("Test lesson");
        request.setDate(Instant.parse("2026-02-06T08:00:00Z"));
        request.setCourseId(10L);

        Lesson lessonEntity = new Lesson("Test lesson", Instant.parse("2026-02-06T08:00:00Z"));
        Course course = new Course();
        course.setCourseID(10L);
        course.setCourseName("Backend Programming");
        course.setLessons(Collections.singleton(lessonEntity));
        lessonEntity.setCourse(course);

        Lesson savedLesson = new Lesson("Test lesson", Instant.parse("2026-02-06T08:00:00Z"));
        savedLesson.setLessonID(1L);
        savedLesson.setCourse(course);

        LessonDto dto = new LessonDto(1L, "Test lesson", Instant.parse("2026-02-06T08:00:00Z"), 10L);

        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(lessonMapper.toLessonEntity(request)).thenReturn(lessonEntity);
        when(lessonRepository.save(lessonEntity)).thenReturn(savedLesson);
        when(lessonMapper.toLessonDto(savedLesson)).thenReturn(dto);

        LessonDto result = service.create(request);

        assertEquals(1L, result.getLessonID());
        assertEquals("Test lesson", result.getLessonName());
        assertEquals(10L, result.getCourseId());
        verify(lessonRepository).save(lessonEntity);
    }

    @Test
    @DisplayName("Lesson is linked to Course correctly")
    void lesson_course_relation() {
        Course course = new Course();
        course.setCourseID(10L);
        course.setCourseName("Backend Programming");

        Lesson lesson = new Lesson("JPA Basics", Instant.parse("2026-02-08T12:00:00Z"));
        lesson.setLessonID(1L);
        lesson.setCourse(course);
        course.setLessons(Collections.singleton(lesson));

        when(lessonRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.getReferenceById(1L)).thenReturn(lesson);

        LessonDto dto = new LessonDto(1L, "JPA Basics", Instant.parse("2026-02-08T12:00:00Z"), 10L);
        when(lessonMapper.toLessonDto(lesson)).thenReturn(dto);

        LessonDto result = service.read(1L);

        assertEquals(1L, result.getLessonID());
        assertEquals("JPA Basics", result.getLessonName());
        assertEquals(10L, result.getCourseId());
        verify(lessonRepository).getReferenceById(1L);
    }

    @Test
    @DisplayName("Successful read")
    void read() {
        Lesson lesson = new Lesson("Name", Instant.parse("2026-02-01T08:00:00Z"));
        lesson.setLessonID(5L);

        when(lessonRepository.existsById(5L)).thenReturn(true);
        when(lessonRepository.getReferenceById(5L)).thenReturn(lesson);

        LessonDto dto = new LessonDto(5L, "Name", Instant.parse("2026-02-01T08:00:00Z"), null);
        when(lessonMapper.toLessonDto(lesson)).thenReturn(dto);

        LessonDto res = service.read(5L);

        assertEquals(5L, res.getLessonID());
        assertEquals("Name", res.getLessonName());
        assertNull(res.getCourseId());
        verify(lessonRepository).getReferenceById(5L);
    }

    @Test
    @DisplayName("Successful update")
    void update() {
        Lesson existing = new Lesson("Old", Instant.parse("2026-02-01T08:00:00Z"));
        existing.setLessonID(5L);

        UpdateLessonRequest request = new UpdateLessonRequest();
        request.setCourseId(10L);

        Course course = new Course();
        course.setCourseID(10L);
        course.setCourseName("Backend Programming");
        existing.setCourse(course);
        course.setLessons(Collections.singleton(existing));

        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(lessonRepository.findById(5L)).thenReturn(Optional.of(existing));

        LessonDto dto = new LessonDto(5L, "Old", Instant.parse("2026-02-01T08:00:00Z"), 10L);
        when(lessonMapper.toLessonDto(existing)).thenReturn(dto);

        LessonDto res = service.update(5L, request);

        verify(lessonMapper).updateLessonEntity(existing, request);
        verify(lessonRepository).save(existing);
        assertEquals(5L, res.getLessonID());
        assertEquals(10L, existing.getCourse().getCourseID());
    }

    @Test
    @DisplayName("Successful deletion")
    void delete() {
        Lesson existing = new Lesson("Name", Instant.parse("2026-02-01T08:00:00Z"));
        existing.setLessonID(5L);

        when(lessonRepository.findById(5L)).thenReturn(Optional.of(existing));

        service.delete(5L);

        verify(lessonRepository).delete(existing);
    }

    @Test
    @DisplayName("read throws if lesson does not exist")
    void read_throwsIfNotExists() {
        when(lessonRepository.existsById(99L)).thenReturn(false);
        assertThrows(BadRequestException.class, () -> service.read(99L));
        verify(lessonRepository, never()).getReferenceById(anyLong());
    }
}