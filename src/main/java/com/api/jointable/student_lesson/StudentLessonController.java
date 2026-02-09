package com.api.jointable.student_lesson;

import com.api.jointable.student_lesson.StudentLessonService;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/{studentID}/attendance")
public class StudentLessonController {

    private final StudentLessonService studentLessonService;

    public StudentLessonController(StudentLessonService studentLessonService) {
        this.studentLessonService = studentLessonService;
    }

    @PostMapping
    public StudentLessonDto postAttendance(
            @PathVariable Integer studentID,
            @Valid @RequestBody CreateStudentLesson request) {
        request.setStudentId(studentID);
        return studentLessonService.create(request);
    }

    @GetMapping("{attendanceID}")
    public StudentLessonDto getAttendance(@PathVariable Long attendanceID) {
        return studentLessonService.read(attendanceID);
    }

    @PutMapping("{attendanceID}")
    public StudentLessonDto putAttendance(
            @PathVariable Long attendanceID,
            @Valid @RequestBody UpdateStudentLesson request) {
        return studentLessonService.update(attendanceID, request);
    }

}