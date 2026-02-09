package com.api.jointable.student_course.dto;

import jakarta.validation.constraints.NotNull;

public class CreateStudentCourse {

    @NotNull
    private Integer studentId;
    @NotNull
    private Long courseId;
    @NotNull
    private Integer grade;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(@NotNull Integer studentId) {
        this.studentId = studentId;
    }

    public @NotNull Long getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull Long courseId) {
        this.courseId = courseId;
    }

    public @NotNull Integer getGrade() {
        return grade;
    }

    public void setGrade(@NotNull Integer grade) {
        this.grade = grade;
    }
}
