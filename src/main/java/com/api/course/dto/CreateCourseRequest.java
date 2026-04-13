package com.api.course.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class CreateCourseRequest {

    @NotBlank
    private String courseName;
    private String locale;

    private Set<Long> lessonIds = new HashSet<>();
    private Set<Long> assignmentIds = new HashSet<>();
    private Set<Integer> teacherIds = new HashSet<>();

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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
