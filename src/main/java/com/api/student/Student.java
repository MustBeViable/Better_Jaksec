package com.api.student;

import com.api.jointable.StudentAssignment;
import com.api.jointable.StudentCourse;
import com.api.jointable.StudentLesson;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Student entity class for ORM structure
 */
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private Integer studentID;

    @Column(name = "FirstName", nullable = false)
    private String firstName;
    @Column(name = "LasttName", nullable = false)
    private String lastName;
    @Column(name = "Email", nullable = false)
    private String email;
    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "student")
    private Set<StudentAssignment> assignments  = new HashSet<>();

    @ManyToMany(mappedBy = "student")
    private Set<StudentCourse> courses = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<StudentLesson> lessons;

    public Student() {}

    public Student(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getStudentID() {
        return this.studentID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //These are here for now in testing purposes

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Set<StudentAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<StudentAssignment> assignments) {
        this.assignments = assignments;
    }

    public Set<StudentCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<StudentCourse> courses) {
        this.courses = courses;
    }

    public Set<StudentLesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<StudentLesson> lessons) {
        this.lessons = lessons;
    }
}
