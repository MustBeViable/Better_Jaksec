package com.api.jointable.student_course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    @Modifying
    @Query("""
    DELETE FROM StudentCourse c
    WHERE c.student.id = :studentId
    AND c.course.id = :courseId
""")
    void deleteFromCourse(Integer studentId, Long courseId);
    List<StudentCourse> findAllByCourse_CourseID(Long courseId);
}
