package com.api.jointable.student_course;

import com.api.common.util.JwtUtils;
import com.api.jointable.student_course.dto.CreateStudentCourse;
import com.api.jointable.student_course.dto.StudentCourseDto;
import com.api.jointable.student_course.dto.UpdateStudentCourse;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.login.Auth;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/grade")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @PostMapping
    public StudentCourseDto postGrade(@RequestHeader("Authorization") String token,
                                      @Valid @RequestBody CreateStudentCourse request) {
        return this.studentCourseService.create(request, JwtUtils.toAuth(token));
    }

    @GetMapping("/course/{courseId}/students")
    public List<Integer> getStudentsOnCourse(@PathVariable Long courseId) {
        return studentCourseService.readStudentIdsByCourse(courseId);
    }

    @GetMapping("{gradeId}")
    public StudentCourseDto getGrade(@RequestHeader("Authorization") String token,
                                     @PathVariable Long gradeId) {
        return studentCourseService.read(gradeId, JwtUtils.toAuth(token));
    }

    @PutMapping("{gradeId}")
    public StudentCourseDto putGrade(@RequestHeader("Authorization") String token,
            @PathVariable Long gradeId,
            @Valid @RequestBody UpdateStudentCourse request) {
        return studentCourseService.update(gradeId, request, JwtUtils.toAuth(token));
    }

    @DeleteMapping("{gradeId}")
    public void deleteGrade(@RequestHeader("Authorization") String token,
                            @PathVariable Long gradeId){
        this.studentCourseService.delete(gradeId, JwtUtils.toAuth(token));
    }
}
