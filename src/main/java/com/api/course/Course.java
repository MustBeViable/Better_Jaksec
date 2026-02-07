package com.api.course;
import com.api.assignment.Assignment;
import com.api.lesson.Lesson;
import com.api.teacher.Teacher;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseID;
    private String courseName;

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons;

    @OneToMany(mappedBy = "course")
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(mappedBy = "courses")
    private Set<Teacher> teachers = new HashSet<>();

    public Course(){}

    public Course(String courseName, Set<Lesson> lessons, Set<Assignment> assignments, Set<Teacher> teachers) {
        this.courseName = courseName;
        this.lessons = lessons;
        this.assignments = assignments;
        this.teachers = teachers;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
