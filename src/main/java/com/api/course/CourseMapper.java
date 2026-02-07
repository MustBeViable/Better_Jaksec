package com.api.course;

import com.api.assignment.Assignment;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.Lesson;
import com.api.teacher.Teacher;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toEmptyCourseEntity(CreateCourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        return course;
    }

    public Course toCourseEntity(CreateCourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        return course;
    }

    public void updateCourseEntity(Course course, UpdateCourseRequest request) {
        if (request.getCourseName() != null) {
            course.setCourseName(request.getCourseName());
        }
    }

    public CourseDto toCourseDto(Course course) {
        return new CourseDto(
                course.getCourseID(),
                course.getCourseName(),
                course.getLessons()
                        .stream()
                        .map(Lesson::getLessonID)
                        .collect(Collectors.toSet()),
                course.getAssignments()
                        .stream()
                        .map(Assignment::getAssignmentID)
                        .collect(Collectors.toSet()),
                course.getTeachers()
                        .stream()
                        .map(Teacher::getTeacherID)
                        .collect(Collectors.toSet())
        );
    }
}
