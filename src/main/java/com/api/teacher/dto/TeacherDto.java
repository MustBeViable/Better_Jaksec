package com.api.teacher.dto;

public class TeacherDto {

    private final Integer teacherID;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean isAdmin;

    public TeacherDto(Integer teacherID, String firstName, String lastName, String email, Boolean isAdmin) {
        this.teacherID = teacherID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        // Can be null due JSON format.
        this.isAdmin = (isAdmin != null) ? isAdmin : false;
    }

    public Integer getTeacherID() {
        return teacherID;
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
