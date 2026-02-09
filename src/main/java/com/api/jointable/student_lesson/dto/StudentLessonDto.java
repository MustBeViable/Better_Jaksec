package com.api.jointable.student_lesson.dto;

import java.time.Instant;

public class StudentLessonDto {

    private Boolean isPresent;
    private Long lessonId;
    private Integer studentId;
    private Instant lessonDate;

    public StudentLessonDto(){}

    public StudentLessonDto(Boolean isPresent, Long lessonId, Integer studentId, Instant lessonDate) {
        this.isPresent = isPresent;
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.lessonDate = lessonDate;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Instant getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Instant lessonDate) {
        this.lessonDate = lessonDate;
    }
}
