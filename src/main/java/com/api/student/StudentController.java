package com.api.student;

import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @RestController notates method to be REST API controller (like in express)
 * For routing it seems to be enough to have @RequestMapping notation to be like this
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public void getStudent(int studentID) {
    }

    @PostMapping
    public StudentDto postStudent(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.create(request);
    }
}
