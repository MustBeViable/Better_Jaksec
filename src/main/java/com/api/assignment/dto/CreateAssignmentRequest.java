package com.api.assignment.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateAssignmentRequest {

    @NotBlank
    private String assignmentName;
    @NotBlank
    private String assignmentDescription;
    private Long courseId;

    public @NotBlank String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(@NotBlank String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public @NotBlank String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(@NotBlank String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
