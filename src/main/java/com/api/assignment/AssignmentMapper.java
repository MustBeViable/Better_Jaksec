package com.api.assignment;

import com.api.assignment.dto.AssignmentDto;
import com.api.assignment.dto.CreateAssignmentRequest;
import com.api.assignment.dto.UpdateAssignmentRequest;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {

    public AssignmentMapper(){}

    public Assignment toEntity(CreateAssignmentRequest request){
        Assignment assignment = new Assignment();
        assignment.setAssignmentDescription(request.getAssignmentDescription());
        assignment.setAssignmentName(request.getAssignmentName());

        return assignment;
    }

    public void updateEntity(Assignment assignment, UpdateAssignmentRequest request){
        if(request.getAssignmentName() != null){
            assignment.setAssignmentName(request.getAssignmentName());
        }
        if(request.getAssignmentDescription() != null){
            assignment.setAssignmentDescription(request.getAssignmentDescription());
        }
    }

    public AssignmentDto toDto(Assignment assignment){
        return new AssignmentDto(assignment.getAssignmentID(),
                assignment.getAssignmentName(),
                assignment.getAssignmentDescription(),
                assignment.getCourse().getCourseID());
    }
}
