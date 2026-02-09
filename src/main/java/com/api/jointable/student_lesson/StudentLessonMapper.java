package com.api.jointable.student_lesson;

import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.lesson.LessonRepository;
import com.api.student.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentLessonMapper {

    public StudentLesson toEntity(CreateStudentLesson request){
        StudentLesson studentLesson = new StudentLesson();
        studentLesson.setPresent(request.getPresent());
        studentLesson.setReasonForAbsence(request.getReason());

        return studentLesson;
    }

    public void updateEntity(StudentLesson entity, UpdateStudentLesson request){
        if(request.getPresent() != null) entity.setPresent(request.getPresent());
        if(request.getReason() != null) entity.setReasonForAbsence(request.getReason());
    }

    public StudentLessonDto toDto(StudentLesson entity){
        return new StudentLessonDto(entity.isPresent(),
                entity.getLesson().getLessonID(),
                entity.getStudent().getStudentID(),
                entity.getLesson().getDate());
    }
}
