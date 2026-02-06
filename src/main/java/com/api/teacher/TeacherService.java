package com.api.teacher;

import com.api.common.error.BadRequestException;
import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Transactional
    public TeacherDto create(CreateTeacherRequest request) {
        if (teacherRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Teacher with this email already exists.");
        }

        Teacher saved = teacherRepository.save(teacherMapper.toTeacherEntity(request));
        return teacherMapper.toTeacherDto(saved);
    }

    @Transactional
    public TeacherDto read(int teacherID) {
        if (!teacherRepository.existsByTeacherID(teacherID)) {
            throw new BadRequestException("Teacher doesn't exist.");
        }

        Teacher teacher = teacherRepository.getReferenceById(teacherID);
        return teacherMapper.toTeacherDto(teacher);
    }

    @Transactional
    public TeacherDto update(int teacherID, UpdateTeacherRequest request) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new BadRequestException("Teacher doesn't exist."));

        teacherMapper.updateTeacherEntity(teacher, request);
        teacherRepository.save(teacher);

        return teacherMapper.toTeacherDto(teacher);
    }

    @Transactional
    public void delete(int teacherID) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new BadRequestException("Teacher doesn't exist."));

        teacherRepository.delete(teacher);
    }
}
