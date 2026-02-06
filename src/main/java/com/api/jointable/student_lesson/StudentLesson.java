package com.api.jointable.student_lesson;

import com.api.lesson.Lesson;
import com.api.student.Student;
import jakarta.persistence.*;

@Entity
public class StudentLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Lesson lesson;
    private Boolean isPresent;
    private String reasonForAbsence;

    public StudentLesson(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public String getReasonForAbsence() {
        return reasonForAbsence;
    }

    public void setReasonForAbsence(String reasonForAbsence) {
        this.reasonForAbsence = reasonForAbsence;
    }
}
