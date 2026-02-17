package com.api.student;

import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.common.error.exceptions.BadRequestException;
import com.api.student.dto.UpdateStudentRequest;
import com.api.teacher.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Student saved = studentRepository.save(studentMapper.toEntity(request));
        return studentMapper.toDto(saved);
    }

    @Transactional
    public List<StudentDto> readAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional
    public StudentDto read(int studentID) {
        if (!studentRepository.existsByStudentID(studentID)) {
            throw new BadRequestException("Student doesn't exists.");
        }

        Student student = studentRepository.getReferenceById(studentID);

        return studentMapper.toDto(student);
    }

    /**
     * Allows email change. If email changing implemented, add checking for emails uniqueness
     * @param studentID
     * @param request
     * @return
     */

    @Transactional
    public StudentDto update(int studentID, UpdateStudentRequest request) {

        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new BadRequestException("Student doesn't exist."));


        studentMapper.updateEntity(student, request);
        studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    @Transactional
    public void delete(int studentID) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new BadRequestException("Student doesn't exist."));

        studentRepository.delete(student);
    }
}
