package com.api.teacher;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.NotFoundException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.login.Auth;
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
    public TeacherDto create(CreateTeacherRequest request, Auth auth) {
        if(!auth.getRole().equalsIgnoreCase("admin")){
            throw new UnauthorizedException("Only admin can create teacher");
        }
        if (!loginService.isEmailAvailable(request.getEmail())) {
            throw new BadRequestException("User with this email already exists.");
        }

        Teacher saved = teacherRepository.save(teacherMapper.toEntity(request));
        return teacherMapper.toDto(saved);
    }

    @Transactional
    public List<TeacherDto> readAll(Auth auth) {
        return teacherRepository.findAll()
               .stream()
               .map(teacherMapper::toDto)
               .toList();
    }

    @Transactional
    public TeacherDto read(int teacherID, Auth auth) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new NotFoundException("Teacher doesn't exist."));
        return teacherMapper.toDto(teacher);
    }

    @Transactional
    public TeacherDto update(int teacherID, UpdateTeacherRequest request,Auth auth) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new BadRequestException("Teacher doesn't exist."));
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getEmail().equalsIgnoreCase(teacher.getEmail())){
            throw new UnauthorizedException("Only admin and self can update teacher");
        }
        teacherMapper.updateEntity(teacher, request);
        teacherRepository.save(teacher);

        return teacherMapper.toDto(teacher);
    }

    @Transactional
    public void delete(int teacherID,Auth auth) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new BadRequestException("Teacher doesn't exist."));

        if (!auth.getRole().equalsIgnoreCase("admin")
                || auth.getEmail().equalsIgnoreCase(teacher.getEmail())) {
            throw new UnauthorizedException("Admin cannot delete themselves");
        }
        teacherRepository.delete(teacher);
    }
}
