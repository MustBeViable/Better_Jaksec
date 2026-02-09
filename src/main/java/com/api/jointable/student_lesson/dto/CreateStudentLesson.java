package com.api.jointable.student_lesson.dto;

import jakarta.validation.constraints.NotNull;

public class CreateStudentLesson {

    private Integer studentId;
    @NotNull
    private Long lessonId;
    @NotNull
    private Boolean present;
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

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
