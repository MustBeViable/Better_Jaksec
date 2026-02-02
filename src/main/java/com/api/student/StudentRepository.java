package com.api.student;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jakarta persistence goes here. Creates interface for repository which lets us use
 * multiple existong methods like existsByEmailIgoneCase
 */

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
