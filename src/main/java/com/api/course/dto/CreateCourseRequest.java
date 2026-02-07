package com.api.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class CreateCourseRequest {

    @NotBlank
    private String courseName;

    private Set<Long> lessonIds;
    private Set<Long> assignmentIds;
    private Set<Integer> teacherIds;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Set<Long> getLessonIds() {
        return lessonIds;
    }

    public void setLessonIds(Set<Long> lessonIds) {
        this.lessonIds = lessonIds;
    }

    public Set<Long> getAssignmentIds() {
        return assignmentIds;
    }

    public void setAssignmentIds(Set<Long> assignmentIds) {
        this.assignmentIds = assignmentIds;
    }

    public Set<Integer> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(Set<Integer> teacherIds) {
        this.teacherIds = teacherIds;
    }
}
