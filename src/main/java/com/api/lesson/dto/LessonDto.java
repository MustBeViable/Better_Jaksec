package com.api.lesson.dto;

import com.api.course.Course;

import java.time.Instant;

public class LessonDto {
    private final Long lessonID;
    private final String lessonName;
    private final Instant date;
    private final Long courseId;

    public LessonDto(Long lessonID, String lessonName, Instant date, Long courseId) {
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.date = date;
        this.courseId = courseId;
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

    public Long getCourseId() {
        return courseId;
    }
}
