package com.api.teacher;

import com.api.common.util.JwtUtils;
import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public TeacherDto postTeacher(@RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateTeacherRequest request) {
        return teacherService.create(request, JwtUtils.toAuth(token));
    }

    @GetMapping("/all")
    public List<TeacherDto> getAllTeachers(@RequestHeader("Authorization") String token) {
        return teacherService.readAll(JwtUtils.toAuth(token));
    }


    @GetMapping("/{teacherID}")
    public TeacherDto getTeacher(@RequestHeader("Authorization") String token,
            @PathVariable int teacherID) {
        return teacherService.read(teacherID,JwtUtils.toAuth(token));
    }

    @PutMapping("/{teacherID}")
    public TeacherDto modifyTeacher(@RequestHeader("Authorization") String token,
            @PathVariable int teacherID,
            @Valid @RequestBody UpdateTeacherRequest request
    ) {
        return teacherService.update(teacherID, request,JwtUtils.toAuth(token));
    }

    @DeleteMapping("/{teacherID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@RequestHeader("Authorization") String token,
            @PathVariable int teacherID) {
        teacherService.delete(teacherID,JwtUtils.toAuth(token));
    }
}
