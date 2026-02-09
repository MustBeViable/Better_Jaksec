package com.api.student.dto;

import com.api.jointable.student_lesson.dto.StudentLessonDto;

import java.util.Set;

/**
 * DTO = Data Transfer Object
 */

public class StudentDto {
    private Integer studentID;
    private String firstName;
    private String lastName;
    private String email;
    private Set<StudentLessonDto> attendance;

    public StudentDto(Integer studentID, String firstName, String lastName, String email) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<StudentLessonDto> getAttendance() {
        return attendance;
    }

    public void setAttendance(Set<StudentLessonDto> attendance) {
        this.attendance = attendance;
    }
}
