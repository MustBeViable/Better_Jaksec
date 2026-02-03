package com.api.student.dto;

/**
 * DTO = Data Transfer Object
 */

public class StudentDto {
    private int studentID;
    private String name;
    private String email;

    public StudentDto(int studentID, String name, String email) {
        this.studentID = studentID;
        this.name = name;
        this.email = email;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
