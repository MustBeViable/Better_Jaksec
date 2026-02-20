package com.api.student;

import com.api.common.error.exceptions.UnauthorizedException;
import com.api.login.Auth;
import com.api.login.LoginService;
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
    private final LoginService loginService;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, LoginService loginService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.loginService = loginService;
    }

    /**
     *
     * @param request
     * @return
     */
    @Transactional
    public StudentDto create(CreateStudentRequest request) {
        if (!loginService.isEmailAvailable(request.getEmail())) {
            throw new BadRequestException("User with this email already exists.");
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
    public StudentDto read(int studentID, Auth auth) {
        System.out.println("StudentService.read.auth: "+auth);

        Student student = this.studentRepository.findById(studentID)
                .orElseThrow(() -> new BadRequestException("Student doesn't exists."));

        if(!auth.getEmail().equals(student.getEmail()) && !auth.getRole().equalsIgnoreCase("student")){
            throw new UnauthorizedException("Unauthorized");
        }
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
