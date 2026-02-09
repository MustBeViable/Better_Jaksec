package com.api.jointable.student_course;

import com.api.jointable.student_course.dto.CreateStudentCourse;
import com.api.jointable.student_course.dto.StudentCourseDto;
import com.api.jointable.student_course.dto.UpdateStudentCourse;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/{studentID}/grade")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @PostMapping
    public StudentCourseDto postGrade(
            @PathVariable Integer studentID,
            @Valid @RequestBody CreateStudentCourse request) {
        return studentCourseService.create(request);
    }

    @GetMapping("{gradeId}")
    public StudentCourseDto getGrade(@PathVariable Long gradeId) {
        return studentCourseService.read(gradeId);
    }

    @PutMapping("{gradeId}")
    public StudentCourseDto putGrade(
            @PathVariable Long gradeId,
            @Valid @RequestBody UpdateStudentCourse request) {
        return studentCourseService.update(gradeId, request);
    }

}
