package com.api.lesson.dto;

import java.time.Instant;

public class LessonDto {
    private final Long lessonID;
    private final String lessonName;
    private final Instant date;

    public LessonDto(Long lessonID, String lessonName, Instant date) {
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.date = date;
    }

    public Long getLessonID() {
        return lessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public Instant getDate() {
        return date;
    }
}
