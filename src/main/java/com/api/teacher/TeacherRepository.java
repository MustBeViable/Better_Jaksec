package com.api.teacher;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByTeacherID(int studentID);
}
