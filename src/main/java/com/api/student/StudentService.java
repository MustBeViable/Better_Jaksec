package com.api.student;

import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.common.error.BadRequestException;
import com.api.student.dto.UpdateStudentRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer handles business logic and ruling
 */

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    /**
     *
     * @param request
     * @return
     */
    @Transactional
    public StudentDto create(CreateStudentRequest request) {
        if (studentRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Studdent with this email already exists.");
        }

        Student saved = studentRepository.save(studentMapper.toStudentEntity(request));
        return studentMapper.toStudentDto(saved);
    }

    @Transactional
    public StudentDto read(int studentID) {
        if (!studentRepository.existsByStudentID(studentID)) {
            throw new BadRequestException("Student doesn't exists.");
        }

        Student student = studentRepository.getReferenceById((long) studentID);

        return studentMapper.toStudentDto(student);
    }

    /**
     * Allows email change. If email changing implemented, add checking for emails uniqueness
     * @param studentID
     * @param request
     * @return
     */

    @Transactional
    public StudentDto update(int studentID, UpdateStudentRequest request) {

        Student student = studentRepository.findById((long) studentID)
                .orElseThrow(() -> new BadRequestException("Student doesn't exist."));


        studentMapper.updateStudentEntity(student, request);
        studentRepository.save(student);

        return studentMapper.toStudentDto(student);
    }

    @Transactional
    public void delete(int studentID) {
        Student student = studentRepository.findById((long) studentID)
                .orElseThrow(() -> new BadRequestException("Student doesn't exist."));

        studentRepository.delete(student);
    }
}
