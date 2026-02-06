package com.api.teacher;

import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher toTeacherEntity(CreateTeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(request.getPassword());
        teacher.setAdmin(request.isAdmin());
        return teacher;
    }

    public void updateTeacherEntity(Teacher teacher, UpdateTeacherRequest request) {

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
            teacher.setPassword(request.getPassword());
        }

        if (request.getAdmin() != null) {
            teacher.setAdmin(request.getAdmin());
        }
    }

    public TeacherDto toTeacherDto(Teacher teacher) {
        return new TeacherDto(
                teacher.getTeacherID(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.isAdmin()
        );
    }
}
