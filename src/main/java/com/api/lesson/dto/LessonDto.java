package com.api.lesson.dto;

import com.api.course.Course;

import java.time.Instant;

public class LessonDto {
    private Long lessonID;
    private String lessonName;
    private String locale;
    private Instant date;
    private Long courseId;

    public LessonDto(){}

    public LessonDto(Long lessonID, String lessonName, String locale, Instant date, Long courseId) {
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.date = date;
        this.courseId = courseId;
        this.locale = locale;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setLessonID(Long lessonID) {
        this.lessonID = lessonID;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
