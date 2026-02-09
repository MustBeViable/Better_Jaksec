package com.api.jointable.student_lesson.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateStudentLesson {

    @NotNull
    private Boolean present;
    private String reason;

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
