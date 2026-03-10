package com.api.assignment;

import com.api.assignment.dto.AssignmentDto;
import com.api.assignment.dto.CreateAssignmentRequest;
import com.api.assignment.dto.UpdateAssignmentRequest;
import com.api.course.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentMapperTest {

    private AssignmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AssignmentMapper();
    }

    @Test
    @DisplayName("toEntity() maps request fields to entity")
    void toEntity_mapsFieldsCorrectly() {
        CreateAssignmentRequest request = new CreateAssignmentRequest();
        request.setAssignmentName("Math homework");
        request.setAssignmentDescription("Solve tasks 1-10");
        request.setCourseId(5L);

        Assignment assignment = mapper.toEntity(request);

        assertAll(
                () -> assertEquals("Math homework", assignment.getAssignmentName()),
                () -> assertEquals("Solve tasks 1-10", assignment.getAssignmentDescription()),
                () -> assertNull(assignment.getCourse()),
                () -> assertNull(assignment.getAssignmentID())
        );
    }

    @Test
    @DisplayName("updateEntity() updates only assignment name when only name is provided")
    void updateEntity_updatesOnlyName() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Old name");
        assignment.setAssignmentDescription("Old description");

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();
        request.setAssignmentName("New name");

        mapper.updateEntity(assignment, request);

        assertAll(
                () -> assertEquals("New name", assignment.getAssignmentName()),
                () -> assertEquals("Old description", assignment.getAssignmentDescription())
        );
    }

    @Test
    @DisplayName("updateEntity() updates only assignment description when only description is provided")
    void updateEntity_updatesOnlyDescription() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Old name");
        assignment.setAssignmentDescription("Old description");

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();
        request.setAssignmentDescription("New description");

        mapper.updateEntity(assignment, request);

        assertAll(
                () -> assertEquals("Old name", assignment.getAssignmentName()),
                () -> assertEquals("New description", assignment.getAssignmentDescription())
        );
    }

    @Test
    @DisplayName("updateEntity() updates both fields when both are provided")
    void updateEntity_updatesBothFields() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Old name");
        assignment.setAssignmentDescription("Old description");

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();
        request.setAssignmentName("New name");
        request.setAssignmentDescription("New description");

        mapper.updateEntity(assignment, request);

        assertAll(
                () -> assertEquals("New name", assignment.getAssignmentName()),
                () -> assertEquals("New description", assignment.getAssignmentDescription())
        );
    }

    @Test
    @DisplayName("updateEntity() does nothing when request fields are null")
    void updateEntity_doesNothingWhenFieldsNull() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Old name");
        assignment.setAssignmentDescription("Old description");

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();

        mapper.updateEntity(assignment, request);

        assertAll(
                () -> assertEquals("Old name", assignment.getAssignmentName()),
                () -> assertEquals("Old description", assignment.getAssignmentDescription())
        );
    }

    @Test
    @DisplayName("toDto() maps entity fields including course id")
    void toDto_mapsFieldsCorrectly() {
        Course course = new Course();
        course.setCourseID(7L);

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(3L);
        assignment.setAssignmentName("Essay");
        assignment.setAssignmentDescription("Write an essay");
        assignment.setCourse(course);

        AssignmentDto dto = mapper.toDto(assignment);

        assertAll(
                () -> assertEquals(3L, dto.getId()),
                () -> assertEquals("Essay", dto.getAssignmentName()),
                () -> assertEquals("Write an essay", dto.getAssignmentDescription()),
                () -> assertEquals(7L, dto.getCourseId())
        );
    }

    @Test
    @DisplayName("toDto() throws when course is null")
    void toDto_throwsWhenCourseNull() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentID(3L);
        assignment.setAssignmentName("Essay");
        assignment.setAssignmentDescription("Write an essay");
        assignment.setCourse(null);

        assertThrows(NullPointerException.class, () -> mapper.toDto(assignment));
    }
}