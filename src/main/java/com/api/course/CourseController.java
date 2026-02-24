package com.api.course;

import com.api.common.util.JwtUtils;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    public CourseDto postCourse(@RequestHeader("Authorization") String token,
                                @Valid @RequestBody CreateCourseRequest request) {
        return this.courseService.create(request, JwtUtils.toAuth(token));
    }

    @GetMapping("/all")
    public List<CourseDto> getCourse(@RequestHeader("Authorization") String token) {
        return this.courseService.readAll(JwtUtils.toAuth(token));
    }

    @GetMapping("/{courseID}")
    public CourseDto getCourse(@RequestHeader("Authorization") String token,
                               @PathVariable Long courseID) {
        return this.courseService.read(courseID, JwtUtils.toAuth(token));
    }

    @PutMapping("/{courseID}")
    public CourseDto putCourse(@RequestHeader("Authorization") String token,
                               @PathVariable Long courseID,
                               @Valid @RequestBody UpdateCourseRequest request) {
        return this.courseService.update(courseID, request, JwtUtils.toAuth(token));
    }

    @DeleteMapping("/{courseID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@RequestHeader("Authorization") String token,
                             @PathVariable Long courseID) {
        this.courseService.delete(courseID, JwtUtils.toAuth(token));
    }

}
