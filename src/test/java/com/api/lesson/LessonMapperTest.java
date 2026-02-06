package com.api.lesson;

import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.UpdateLessonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LessonMapperTest {

    private LessonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new LessonMapper();
    }

    @Test
    @DisplayName("toLessonEntity() maps fields correctly and does not mutate input")
    void toLessonEntity() {
        CreateLessonRequest request = new CreateLessonRequest();
        request.setLessonName("Test lesson");
        request.setDate(Instant.parse("2026-02-06T08:00:00Z"));

        Lesson lesson = mapper.toLessonEntity(request);

        assertAll(
                () -> assertEquals("Test lesson", lesson.getLessonName()),
                () -> assertEquals(Instant.parse("2026-02-06T08:00:00Z"), lesson.getDate())
        );
    }

    @Test
    @DisplayName("updateLessonEntity() updates only provided fields")
    void updateLessonEntity_partialUpdate() {
        Lesson lesson = new Lesson();
        lesson.setLessonName("Original");
        lesson.setDate(Instant.parse("2026-02-01T08:00:00Z"));

        UpdateLessonRequest request = new UpdateLessonRequest();
        request.setLessonName("Updated");

        mapper.updateLessonEntity(lesson, request);

        assertAll(
                () -> assertEquals("Updated", lesson.getLessonName()),
                () -> assertEquals(
                        Instant.parse("2026-02-01T08:00:00Z"),
                        lesson.getDate()
                )
        );
    }

    @Test
    @DisplayName("updateLessonEntity() updates all fields")
    void updateLessonEntity_fullUpdate() {
        Lesson lesson = new Lesson();
        lesson.setLessonName("Original");
        lesson.setDate(Instant.parse("2026-02-01T08:00:00Z"));

        UpdateLessonRequest request = new UpdateLessonRequest();
        request.setLessonName("Updated");
        request.setDate(Instant.parse("2026-02-10T08:00:00Z"));

        mapper.updateLessonEntity(lesson, request);

        assertAll(
                () -> assertEquals("Updated", lesson.getLessonName()),
                () -> assertEquals(
                        Instant.parse("2026-02-10T08:00:00Z"),
                        lesson.getDate()
                )
        );
    }

    @Test
    @DisplayName("updateLessonEntity() with empty request does not crash")
    void updateLessonEntity_emptyRequest() {
        Lesson lesson = new Lesson();
        lesson.setLessonName("Original");
        lesson.setDate(Instant.parse("2026-02-01T08:00:00Z"));

        UpdateLessonRequest request = new UpdateLessonRequest();

        assertDoesNotThrow(() ->
                mapper.updateLessonEntity(lesson, request)
        );
    }
}
