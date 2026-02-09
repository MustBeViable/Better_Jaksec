package com.api.jointable.student_lesson.dto;

import jakarta.validation.constraints.NotNull;

public class CreateStudentLesson {

    @NotNull
    private Integer studentId;
    @NotNull
    private Long lessonId;
    @NotNull
    private Boolean absent;
    private String reason;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Boolean getAbsent() {
        return absent;
    }

    public void setAbsent(Boolean absent) {
        this.absent = absent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
