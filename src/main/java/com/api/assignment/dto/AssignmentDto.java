package com.api.assignment.dto;

import com.api.course.Course;

public class AssignmentDto {

    private Long id;
    private String assignmentName;
    private String assignmentDescription;
    private Long courseId;

    public AssignmentDto() {}

    public AssignmentDto(Long id, String assignmentName, String assignmentDescription, Long courseId) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.assignmentDescription = assignmentDescription;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourse(Long course) {
        this.courseId = course;
    }
}
