package com.api.assignment;

import com.api.assignment.dto.AssignmentDto;
import com.api.assignment.dto.CreateAssignmentRequest;
import com.api.assignment.dto.UpdateAssignmentRequest;
import com.api.common.error.exceptions.BadRequestException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final AssignmentMapper mapper;

    public AssignmentService(AssignmentRepository assignmentRepository, CourseRepository courseRepository, AssignmentMapper mapper) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AssignmentDto create(CreateAssignmentRequest request) {
        Assignment assignment = this.mapper.toEntity(request);

        Course course = this.courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course doesn't exist"));

        assignment.setCourse(course);

        this.assignmentRepository.save(assignment);
        return this.mapper.toDto(assignment);
    }

    @Transactional
    public AssignmentDto read(Long assignmentId){
        Assignment assignment = this.assignmentRepository.findById(assignmentId)
                .orElseThrow( () -> new BadRequestException("Assignment doesnt exist"));
        return this.mapper.toDto(assignment);
    }

    @Transactional
    public AssignmentDto update(Long assignmentId, UpdateAssignmentRequest request){
        Assignment assignment = this.assignmentRepository.findById(assignmentId)
                .orElseThrow(()->new BadRequestException("Assignment doesnt exit"));
        this.mapper.updateEntity(assignment,request);

        if (request.getCourseId() != null) {
            Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("Course doesn't exist."));
            assignment.setCourse(course);
        }
        this.assignmentRepository.save(assignment);
        return this.mapper.toDto(assignment);
    }

    @Transactional
    public void delete(Long assignmentId) {
        Assignment assignment = this.assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new BadRequestException("Assignment doesn't exist."));

        this.assignmentRepository.delete(assignment);

    }
}
