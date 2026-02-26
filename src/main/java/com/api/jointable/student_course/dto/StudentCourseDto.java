package com.api.jointable.student_course.dto;

import com.api.course.Course;
import com.api.student.Student;

public class StudentCourseDto {

    private Long gradeId;
    private Integer studentId;
    private Long courseId;
    private Integer grade;

    public StudentCourseDto(){}

    public StudentCourseDto(Long gradeId,Integer studentId, Long courseId, Integer grade) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
