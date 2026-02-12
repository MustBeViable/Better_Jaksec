package com.api.login;

public abstract class User {

    public abstract Integer getId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    public abstract String getRole();

    public abstract String getPassword();
}