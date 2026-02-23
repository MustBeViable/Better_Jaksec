package com.api.lesson;

import com.api.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("""
        SELECT l
        FROM Lesson l
        JOIN l.students sl
        WHERE LOWER(sl.student.email) = LOWER(:email)
    """)
    List<Lesson> findLessonsByStudentEmail(String email);
}
