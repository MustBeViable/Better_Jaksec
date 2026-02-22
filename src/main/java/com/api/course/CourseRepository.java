package com.api.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
        SELECT c
        FROM Course c
        JOIN c.grades g
        WHERE LOWER(g.student.email) = LOWER(:email)
    """)
    List<Course> findCoursesByStudentEmail(String email);

    @Query("""
        SELECT c
        FROM Course c
        JOIN c.teachers t
        WHERE LOWER(t.email) = LOWER(:email)
    """)
    List<Course> findCoursesByTeacherEmail(String email);
}
