package com.api.jointable.student_lesson.dto;

import java.time.Instant;

public class StudentLessonDto {

    private Long id;
    private Boolean present;
    private Long lessonId;
    private Integer studentId;
    private Instant lessonDate;
    private String reason;

    public StudentLessonDto(){}

    public StudentLessonDto(Long id,Boolean present, Long lessonId, Integer studentId, Instant lessonDate, String reason) {
        this.id=id;
        this.present = present;
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.lessonDate = lessonDate;
        this.reason = reason;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
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

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }
}
