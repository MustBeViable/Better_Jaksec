package com.api.lesson;

import com.api.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("""
        SELECT l
        FROM Lesson l
        JOIN l.students sl
        WHERE LOWER(sl.student.email) = LOWER(:email)
    """)
    List<Lesson> findLessonsByStudentEmail(String email);

    @Query("""
           SELECT CASE WHEN COUNT(sl) > 0 THEN true ELSE false END
           FROM StudentLesson sl
           JOIN sl.student s
           JOIN sl.lesson l
           WHERE l.id = :lessonId AND s.email = :studentEmail
           """)
    boolean isStudentEnrolledInLesson(@Param("studentEmail") String studentEmail,
                                      @Param("lessonId") Long lessonId);
}
