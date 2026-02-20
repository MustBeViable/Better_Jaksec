package com.api.login;

public class Auth {

    private String role;
    private String email;

    public Auth(String role, String email) {
        this.role = role;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
