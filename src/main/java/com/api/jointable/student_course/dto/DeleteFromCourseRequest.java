package com.api.jointable.student_course.dto;

import jakarta.validation.constraints.NotNull;

public class DeleteFromCourseRequest {

    @NotNull
    private Integer studentId;
    @NotNull
    private Long courseId;

    public DeleteFromCourseRequest(){}

    public DeleteFromCourseRequest(Integer studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public @NotNull Long getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull Long courseId) {
        this.courseId = courseId;
    }

    public @NotNull Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(@NotNull Integer studentId) {
        this.studentId = studentId;
    }
}
