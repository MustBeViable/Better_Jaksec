package com.api.assignment;

import com.api.assignment.dto.AssignmentDto;
import com.api.assignment.dto.CreateAssignmentRequest;
import com.api.assignment.dto.UpdateAssignmentRequest;
import com.api.course.CourseService;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public AssignmentDto postAssignment(@Valid @RequestBody CreateAssignmentRequest request) {
        return this.assignmentService.create(request);
    }

    @GetMapping("{assignmentID}")
    public AssignmentDto getAssignment(@PathVariable Long assignmentID) {
        return this.assignmentService.read(assignmentID);
    }

    @PutMapping("{assignmentID}")
    public AssignmentDto putAssignment(@PathVariable Long assignmentID, @Valid @RequestBody UpdateAssignmentRequest request) {
        return this.assignmentService.update(assignmentID, request);
    }

    @DeleteMapping("{assignmentID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable Long assignmentID) {
        this.assignmentService.delete(assignmentID);
    }

}
