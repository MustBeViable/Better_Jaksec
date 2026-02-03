package com.api.student.dto;

/**
 * Allows now email change. If we want to keep the email change, we need to check uniqueness of email in student service
 */

public class UpdateStudentRequest {

    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
