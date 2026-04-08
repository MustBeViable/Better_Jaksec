package com.api.student;

import com.api.common.error.exceptions.NotFoundException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.login.Auth;
import com.api.login.LoginService;
import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.common.error.exceptions.BadRequestException;
import com.api.student.dto.UpdateStudentRequest;
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
     * @param auth
     * @return
     */
    @Transactional
    public StudentDto create(CreateStudentRequest request, Auth auth) {
        if(!auth.getRole().equalsIgnoreCase("admin")){
            throw new UnauthorizedException("Only admin can create a student");
        }
        if (!loginService.isEmailAvailable(request.getEmail())) {
            throw new BadRequestException("User with this email already exists.");
        }

        Student saved = studentRepository.save(studentMapper.toEntity(request));
        return studentMapper.toDto(saved);
    }

    @Transactional
    public List<StudentDto> readAll(Auth auth) {
        if(auth.getRole().equalsIgnoreCase("admin") || auth.getRole().equalsIgnoreCase("teacher")){
            return studentRepository.findAll()
                    .stream()
                    .map(studentMapper::toDto)
                    .toList();

        }
        else {
            throw new UnauthorizedException("Only admin can fetch all students");
        }
    }

    @Transactional
    public StudentDto read(int studentID, Auth auth) {
        System.out.println("StudentService.read.auth: "+auth);

        Student student = this.studentRepository.findById(studentID)
                .orElseThrow(() -> new BadRequestException("Student doesn't exists."));

        String role = auth.getRole() == null ? "" : auth.getRole().toLowerCase();

        switch (role) {
            case "admin", "teacher":
                return studentMapper.toDto(student);

            case "student":
                if (!auth.getEmail().equalsIgnoreCase(student.getEmail())) {
                    throw new UnauthorizedException("Unauthorized");
                }
                return studentMapper.toDto(student);

            default:
                throw new UnauthorizedException("Invalid token");
        }
    }

    /**
     * Allows email change. If email changing implemented, add checking for emails uniqueness
     * @param studentID
     * @param request
     * @return
     */

    @Transactional
    public StudentDto update(int studentID, UpdateStudentRequest request, Auth auth) {

        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new NotFoundException("Student doesn't exist."));
        if(!auth.getRole().equalsIgnoreCase("admin")
            && !auth.getEmail().equalsIgnoreCase(student.getEmail())){
            throw new UnauthorizedException("Only admin and self can update student");
        }
        studentMapper.updateEntity(student, request);
        studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    @Transactional
    public void delete(int studentID, Auth auth) {
        if(!auth.getRole().equalsIgnoreCase("admin")){
            throw new UnauthorizedException("Only admin can delete student");
        }
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new NotFoundException("Student doesn't exist."));
        studentRepository.delete(student);
    }
}
