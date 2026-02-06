package com.api.jointable.student_course;

import com.api.course.Course;
import com.api.student.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "courseGrade")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course assignment;
    private Integer grade;

    public StudentCourse(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getAssignment() {
        return assignment;
    }

    public void setAssignment(Course assignment) {
        this.assignment = assignment;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
