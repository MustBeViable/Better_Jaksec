package com.api.teacher;

import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public TeacherDto postTeacher(@Valid @RequestBody CreateTeacherRequest request) {
        return teacherService.create(request);
    }

    @GetMapping("/all")
    public List<TeacherDto> getAllTeachers() {
        return teacherService.readAll();
    }


    @GetMapping("/{teacherID}")
    public TeacherDto getTeacher(@PathVariable int teacherID) {
        return teacherService.read(teacherID);
    }

    @PutMapping("/{teacherID}")
    public TeacherDto modifyTeacher(
            @PathVariable int teacherID,
            @Valid @RequestBody UpdateTeacherRequest request
    ) {
        return teacherService.update(teacherID, request);
    }

    @DeleteMapping("/{teacherID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable int teacherID) {
        teacherService.delete(teacherID);
    }
}
