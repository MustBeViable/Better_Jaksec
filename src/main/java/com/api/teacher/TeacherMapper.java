package com.api.teacher;

import com.api.common.util.HashUtils;
import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher toEntity(CreateTeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(HashUtils.hash(request.getPassword()));
        teacher.setAdmin(request.isAdmin());
        return teacher;
    }

    public void updateEntity(Teacher teacher, UpdateTeacherRequest request) {

        if (request.getFirstName() != null) {
            teacher.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            teacher.setLastName(request.getLastName());
        }

        if (request.getEmail() != null) {
            teacher.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            teacher.setPassword(HashUtils.hash(request.getPassword()));
        }

        if (request.getAdmin() != null) {
            teacher.setAdmin(request.getAdmin());
        }
    }

    public TeacherDto toDto(Teacher teacher) {
        return new TeacherDto(
                teacher.getTeacherID(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.isAdmin()
        );
    }
}
