package com.api.student;

import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.student.dto.UpdateStudentRequest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper handles Entity <-> DTO changes
 */

@Component
public class StudentMapper {

    public Student toStudentEntity(CreateStudentRequest request) {
        return new Student(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
    }

    public void updateStudentEntity(Student student, UpdateStudentRequest request) {
        if (request.getFirstName() != null) student.setFirstName(request.getFirstName());
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null) student.setEmail(request.getEmail());
        if (request.getPassword() != null) student.setPassword(request.getPassword());
    }

    public StudentDto toStudentDto(Student student) {
        StudentDto dto = new StudentDto(
                student.getStudentID(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );

        if (student.getLessons() != null) {
            Set<StudentLessonDto> attendance = student.getLessons()
                    .stream()
                    .map(sl -> new StudentLessonDto(
                            sl.isPresent(),
                            sl.getLesson().getLessonID(),
                            sl.getStudent().getStudentID(),
                            sl.getLesson().getDate()
                    ))
                    .collect(Collectors.toSet());
            dto.setAttendance(attendance);
        }

        return dto;
    }
}
