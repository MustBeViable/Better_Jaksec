package com.api.student;

import com.api.common.util.JwtUtils;
import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.student.dto.UpdateStudentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public StudentDto postStudent(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.create(request);
    }

    @GetMapping("/{studentID}")
    public StudentDto getStudent(@RequestHeader("Authorization") String token, @PathVariable int studentID) {
        System.out.println("StudentController.getStudent.token: "+ token);
        return studentService.read(studentID, JwtUtils.toAuth(token));
    }

    @PutMapping("/{studentID}")
    public StudentDto modifyStudent(@PathVariable int studentID, UpdateStudentRequest request) {
        return studentService.update(studentID, request);
    }

    @DeleteMapping("/{studentID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable int studentID) {
        studentService.delete(studentID);
    }
}
