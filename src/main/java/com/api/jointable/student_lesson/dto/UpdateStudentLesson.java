package com.api.jointable.student_lesson.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateStudentLesson {

    private Boolean absent;
    private String reason;

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
