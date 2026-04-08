package com.api.lesson;

import com.api.common.Language;
import com.api.course.Course;
import com.api.jointable.student_lesson.StudentLesson;
import com.api.teacher.Teacher;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonID")
    private Long lessonID;

    @Column(name = "lessonName")
    private String lessonName;
    @Column(name = "date")
    private Instant date;

    @ManyToOne
    private Language language;

    @ManyToOne
    private Course course;

    @ManyToMany
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentLesson> students = new HashSet<>();

    public Lesson() {}

    public Lesson(String lessonName, Instant date) {
        this.lessonName = lessonName;
        this.date = date;
    }

    public Long getLessonID() {
        return lessonID;
    }

    public void setLessonID(Long lessonID) {
        this.lessonID = lessonID;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<StudentLesson> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentLesson> students) {
        this.students = students;
    }
}
