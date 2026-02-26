package com.api.jointable.student_course;

import com.api.jointable.student_course.dto.CreateStudentCourse;
import com.api.jointable.student_course.dto.StudentCourseDto;
import com.api.jointable.student_course.dto.UpdateStudentCourse;
import org.springframework.stereotype.Component;

@Component
public class StudentCourseMapper {

    public StudentCourse toEntity(CreateStudentCourse request){
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setGrade(request.getGrade());

        return studentCourse;
    }

    public void updateEntity(StudentCourse entity, UpdateStudentCourse request){
        if(request.getGrade() != null){
            entity.setGrade(request.getGrade());
        }
    }

    public StudentCourseDto toDto(StudentCourse entity){
        return new StudentCourseDto(entity.getId(),
                entity.getStudent().getStudentID(),
                entity.getCourse().getCourseID(),
                entity.getGrade());
    }
}
