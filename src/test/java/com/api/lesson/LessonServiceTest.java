package com.api.lesson;

import com.api.common.error.BadRequestException;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    private LessonRepository repo;
    private LessonMapper mapper;
    private LessonService service;

    @BeforeEach
    void setUp() {
        repo = mock(LessonRepository.class);
        mapper = mock(LessonMapper.class);
        service = new LessonService(repo, mapper);
    }

    @Test
    @DisplayName("Successful creation")
    void create_savesEntity_returnsDto() {
        CreateLessonRequest request = new CreateLessonRequest();
        request.setLessonName("Test lesson");
        request.setDate(Instant.parse("2026-02-06T08:00:00Z"));

        Lesson lessonEntity = new Lesson("Test lesson", Instant.parse("2026-02-06T08:00:00Z"));
        Lesson savedLesson = new Lesson("Test lesson", Instant.parse("2026-02-06T08:00:00Z"));
        savedLesson.setLessonID(1L);

        LessonDto dto = new LessonDto(1L, "Test lesson", Instant.parse("2026-02-06T08:00:00Z"));

        when(mapper.toLessonEntity(request)).thenReturn(lessonEntity);
        when(repo.save(lessonEntity)).thenReturn(savedLesson);
        when(mapper.toLessonDto(savedLesson)).thenReturn(dto);

        LessonDto result = service.create(request);

        assertEquals("Test lesson", result.getLessonName());
        assertEquals(1L, result.getLessonID());
        verify(repo).save(lessonEntity);
    }

    @Test
    @DisplayName("Successful read")
    void read() {
        when(repo.existsById(5L)).thenReturn(true);

        Lesson lesson = new Lesson("Name", Instant.parse("2026-02-01T08:00:00Z"));
        lesson.setLessonID(5L);

        when(repo.getReferenceById(5L)).thenReturn(lesson);

        LessonDto dto = new LessonDto(5L, "Name", Instant.parse("2026-02-01T08:00:00Z"));
        when(mapper.toLessonDto(lesson)).thenReturn(dto);

        LessonDto res = service.read(5L);

        assertEquals("Name", res.getLessonName());
        assertEquals(5L, res.getLessonID());
        verify(repo).getReferenceById(5L);
    }

    @Test
    @DisplayName("Successful update")
    void update() {
        Lesson existing = new Lesson("Old", Instant.parse("2026-02-01T08:00:00Z"));
        existing.setLessonID(5L);

        when(repo.findById(5L)).thenReturn(Optional.of(existing));

        UpdateLessonRequest request = new UpdateLessonRequest();

        LessonDto dto = new LessonDto(5L, "Old", Instant.parse("2026-02-01T08:00:00Z"));
        when(mapper.toLessonDto(existing)).thenReturn(dto);

        LessonDto res = service.update(5L, request);

        verify(mapper).updateLessonEntity(existing, request);
        verify(repo).save(existing);
        assertEquals(5L, res.getLessonID());
    }

    @Test
    @DisplayName("Successful deletion")
    void delete() {
        Lesson existing = new Lesson("Name", Instant.parse("2026-02-01T08:00:00Z"));
        existing.setLessonID(5L);

        when(repo.findById(5L)).thenReturn(Optional.of(existing));

        service.delete(5L);

        verify(repo).delete(existing);
    }

    @Test
    @DisplayName("read throws if lesson does not exist")
    void read_throwsIfNotExists() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(BadRequestException.class, () -> service.read(99L));
        verify(repo, never()).getReferenceById(anyLong());
    }
}
