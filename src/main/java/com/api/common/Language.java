package com.api.common;

import com.api.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Table(name = "language")
@Entity
public class Language {

    /**
     * Locale in format ll_CC (e.g. en_US, fi_FI)
     */
    @Id
    private String locale;

    @OneToMany(mappedBy = "language")
    private Set<Course> courses;

    public Language(String locale) {
        this.locale = locale;
    }

    public Language(){}

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return this.locale;
    }
}
