package com.api.teacher;

import com.api.common.error.exceptions.BadRequestException;
import com.api.login.LoginService;
import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final LoginService loginService;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper, LoginService loginService) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.loginService = loginService;
    }

    @Transactional
    public TeacherDto create(CreateTeacherRequest request) {
        if (!loginService.isEmailAvailable(request.getEmail())) {
            throw new BadRequestException("User with this email already exists.");
        }

        Teacher saved = teacherRepository.save(teacherMapper.toEntity(request));
        return teacherMapper.toDto(saved);
    }

    @Transactional
    public List<TeacherDto> readAll() {
       return teacherRepository.findAll()
               .stream()
               .map(teacherMapper::toDto)
               .toList();
    }

    @Transactional
    public TeacherDto read(int teacherID) {
        if (!teacherRepository.existsByTeacherID(teacherID)) {
            throw new BadRequestException("Teacher doesn't exist.");
        }

        Teacher teacher = teacherRepository.getReferenceById(teacherID);
        return teacherMapper.toDto(teacher);
    }

    @Transactional
    public TeacherDto update(int teacherID, UpdateTeacherRequest request) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new BadRequestException("Teacher doesn't exist."));

        teacherMapper.updateEntity(teacher, request);
        teacherRepository.save(teacher);

        return teacherMapper.toDto(teacher);
    }

    @Transactional
    public void delete(int teacherID) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new BadRequestException("Teacher doesn't exist."));

        teacherRepository.delete(teacher);
    }
}
