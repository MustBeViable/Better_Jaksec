package com.api.student;

import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import org.springframework.stereotype.Component;

/**
 * Mapper handles Entity <-> DTO changes
 */

@Component
public class StudentMapper {

    public Student toStudentEntity(CreateStudentRequest request) {
        return new Student(request.getName(), request.getEmail(), request.getPassword());
    }

    public StudentDto toStudentDto(Student student) {
        return new StudentDto(student.getStudentID(), student.getName(), student.getEmail());
    }
}
