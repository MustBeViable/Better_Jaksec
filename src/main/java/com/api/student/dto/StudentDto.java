package com.api.student.dto;

/**
 * DTO = Data Transfer Object
 */

public class StudentDto {
    private Integer studentID;
    private String firstName;
    private String lastName;
    private String email;

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
}
