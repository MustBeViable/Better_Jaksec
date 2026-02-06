package com.api.lesson.dto;

import java.time.Instant;

public class UpdateLessonRequest {

    private String lessonName;
    private Instant date;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
