package com.api.course;

import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
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
    public CourseDto postCourse(@Valid @RequestBody CreateCourseRequest request) {
        return this.courseService.create(request);
    }

    @GetMapping("/all")
    public List<CourseDto> getCourse() {
        return this.courseService.readAll();
    }

    @GetMapping("{courseID}")
    public CourseDto getCourse(@PathVariable Long courseID) {
        return this.courseService.read(courseID);
    }

    @PutMapping("{courseID}")
    public CourseDto putCourse(@PathVariable Long courseID, UpdateCourseRequest request) {
        return this.courseService.update(courseID, request);
    }

    @DeleteMapping("{courseID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long courseID) {
        this.courseService.delete(courseID);
    }

}
