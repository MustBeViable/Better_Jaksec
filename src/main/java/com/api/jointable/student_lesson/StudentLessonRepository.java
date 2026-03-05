package com.api.jointable.student_lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {

    @Query("""
        SELECT DISTINCT sl
        FROM StudentLesson sl
        JOIN sl.lesson l
        JOIN l.teachers t
        WHERE l.id = :lessonId
        AND t.email = :email
    """)
    Set<StudentLesson> getStudentLessonByLessonId(Long lessonId, String email);

    @Query("""
        SELECT DISTINCT sl
        FROM StudentLesson sl
        JOIN sl.lesson l
        WHERE l.lessonID = :lessonId
    """)
    Set<StudentLesson> getStudentLessonByLessonId(Long lessonId);

    @Query("""
        SELECT COUNT(sl) > 0
        FROM StudentLesson sl
        WHERE sl.student.studentID = :studentId
        AND sl.lesson.lessonID = :lessonId
        """)
    boolean existsByStudentIdAndLessonId(Integer studentId, Long lessonId);

    @Query("""
        SELECT sl
        FROM StudentLesson sl
        WHERE sl.student.studentID = :studentId
        AND sl.lesson.lessonID = :lessonId
        """)
    StudentLesson findByStudentIdAndLessonId(Integer studentId, Long lessonId);
}
