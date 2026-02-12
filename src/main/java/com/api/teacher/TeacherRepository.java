package com.api.teacher;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByTeacherID(int studentID);
    Optional<Teacher> findByEmailIgnoreCase(String email);

}
