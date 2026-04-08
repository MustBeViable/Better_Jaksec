package com.api.assignment;

import com.api.assignment.dto.AssignmentDto;
import com.api.assignment.dto.CreateAssignmentRequest;
import com.api.assignment.dto.UpdateAssignmentRequest;
import com.api.common.error.exceptions.BadRequestException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentServiceTest {

    private AssignmentRepository assignmentRepository;
    private CourseRepository courseRepository;
    private AssignmentMapper mapper;
    private AssignmentService service;

    @BeforeEach
    void setUp() {
        assignmentRepository = mock(AssignmentRepository.class);
        courseRepository = mock(CourseRepository.class);
        mapper = mock(AssignmentMapper.class);

        service = new AssignmentService(assignmentRepository, courseRepository, mapper);
    }

    @Test
    @DisplayName("create() maps request, sets course when found, saves and returns dto")
    void create_setsCourse_savesAndReturnsDto() {
        CreateAssignmentRequest request = new CreateAssignmentRequest();
        request.setAssignmentName("Assignment 1");
        request.setAssignmentDescription("Description");
        request.setCourseId(10L);

        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Assignment 1");
        assignment.setAssignmentDescription("Description");

        Course course = new Course();
        course.setCourseID(10L);

        AssignmentDto dto = new AssignmentDto(1L, "Assignment 1", "Description", 10L);

        when(mapper.toEntity(request)).thenReturn(assignment);
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(mapper.toDto(assignment)).thenReturn(dto);

        AssignmentDto result = service.create(request);

        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Assignment 1", result.getAssignmentName()),
                () -> assertEquals("Description", result.getAssignmentDescription()),
                () -> assertEquals(10L, result.getCourseId()),
                () -> assertEquals(course, assignment.getCourse())
        );

        verify(mapper).toEntity(request);
        verify(courseRepository).findById(10L);
        verify(assignmentRepository).save(assignment);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("create() throws BadRequestException when course does not exist")
    void create_throwsWhenCourseNotFound() {
        CreateAssignmentRequest request = new CreateAssignmentRequest();
        request.setAssignmentName("Assignment 1");
        request.setAssignmentDescription("Description");
        request.setCourseId(10L);

        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Assignment 1");
        assignment.setAssignmentDescription("Description");

        when(mapper.toEntity(request)).thenReturn(assignment);
        when(courseRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.create(request));

        verify(mapper).toEntity(request);
        verify(courseRepository).findById(10L);
        verify(assignmentRepository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    @DisplayName("read() returns dto when assignment exists")
    void read_returnsDtoWhenFound() {
        Long assignmentId = 5L;

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentId);

        AssignmentDto dto = new AssignmentDto(assignmentId, "Assignment", "Description", 2L);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(mapper.toDto(assignment)).thenReturn(dto);

        AssignmentDto result = service.read(assignmentId);

        assertAll(
                () -> assertEquals(assignmentId, result.getId()),
                () -> assertEquals("Assignment", result.getAssignmentName())
        );

        verify(assignmentRepository).findById(assignmentId);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("read() throws BadRequestException when assignment does not exist")
    void read_throwsWhenNotFound() {
        Long assignmentId = 5L;

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.read(assignmentId));

        verify(assignmentRepository).findById(assignmentId);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("update() updates entity fields, sets new course when found, saves and returns dto")
    void update_updatesFieldsAndCourse() {
        Long assignmentId = 8L;

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();
        request.setAssignmentName("Updated assignment");
        request.setAssignmentDescription("Updated description");
        request.setCourseId(20L);

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentId);
        assignment.setAssignmentName("Old");
        assignment.setAssignmentDescription("Old desc");

        Course newCourse = new Course();
        newCourse.setCourseID(20L);

        AssignmentDto dto = new AssignmentDto(assignmentId, "Updated assignment", "Updated description", 20L);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(courseRepository.findById(20L)).thenReturn(Optional.of(newCourse));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(mapper.toDto(assignment)).thenReturn(dto);

        AssignmentDto result = service.update(assignmentId, request);

        assertEquals(assignmentId, result.getId());
        assertEquals(newCourse, assignment.getCourse());

        verify(assignmentRepository).findById(assignmentId);
        verify(mapper).updateEntity(assignment, request);
        verify(courseRepository, times(2)).findById(20L);
        verify(assignmentRepository).save(assignment);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("update() updates fields but does not change course when courseId is null")
    void update_doesNotChangeCourseWhenCourseIdNull() {
        Long assignmentId = 8L;

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();
        request.setAssignmentName("Updated assignment");
        request.setAssignmentDescription("Updated description");
        request.setCourseId(null);

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentId);

        AssignmentDto dto = new AssignmentDto(assignmentId, "Updated assignment", "Updated description", 3L);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(mapper.toDto(assignment)).thenReturn(dto);

        AssignmentDto result = service.update(assignmentId, request);

        assertEquals(assignmentId, result.getId());

        verify(assignmentRepository).findById(assignmentId);
        verify(mapper).updateEntity(assignment, request);
        verify(courseRepository, never()).findById(anyLong());
        verify(assignmentRepository).save(assignment);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("update() does not change course when given courseId is not found")
    void update_doesNotChangeCourseWhenCourseNotFound() {
        Long assignmentId = 8L;

        UpdateAssignmentRequest request = new UpdateAssignmentRequest();
        request.setCourseId(55L);

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentId);

        AssignmentDto dto = new AssignmentDto(assignmentId, "Name", "Desc", 3L);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(courseRepository.findById(55L)).thenReturn(Optional.empty());
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(mapper.toDto(assignment)).thenReturn(dto);

        AssignmentDto result = service.update(assignmentId, request);

        assertEquals(assignmentId, result.getId());

        verify(mapper).updateEntity(assignment, request);
        verify(courseRepository).findById(55L);
        verify(assignmentRepository).save(assignment);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("update() throws BadRequestException when assignment does not exist")
    void update_throwsWhenAssignmentNotFound() {
        Long assignmentId = 8L;
        UpdateAssignmentRequest request = new UpdateAssignmentRequest();

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.update(assignmentId, request));

        verify(assignmentRepository).findById(assignmentId);
        verifyNoMoreInteractions(assignmentRepository);
        verifyNoInteractions(courseRepository, mapper);
    }

    @Test
    @DisplayName("delete() deletes by id when assignment exists")
    void delete_deletesByIdWhenFound() {
        Long assignmentId = 4L;

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentId);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.existsById(assignmentId)).thenReturn(true);

        service.delete(assignmentId);

        verify(assignmentRepository).findById(assignmentId);
        verify(assignmentRepository).existsById(assignmentId);
        verify(assignmentRepository).deleteById(assignmentId);
    }

    @Test
    @DisplayName("delete() throws BadRequestException when assignment does not exist")
    void delete_throwsWhenNotFound() {
        Long assignmentId = 4L;

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.delete(assignmentId));

        verify(assignmentRepository).findById(assignmentId);
        verify(assignmentRepository, never()).existsById(anyLong());
        verify(assignmentRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("delete() does nothing after find when existsById returns false")
    void delete_doesNothingWhenExistsByIdFalseAfterFind() {
        Long assignmentId = 4L;

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentId);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.existsById(assignmentId)).thenReturn(false);

        assertDoesNotThrow(() -> service.delete(assignmentId));

        verify(assignmentRepository).findById(assignmentId);
        verify(assignmentRepository).existsById(assignmentId);
        verify(assignmentRepository, never()).deleteById(assignmentId);
    }
}