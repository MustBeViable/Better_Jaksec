package com.api.student;

import com.api.common.error.BadRequestException;
import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.student.dto.UpdateStudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository repository;
    private StudentMapper mapper;
    private StudentService service;

    //Creates mock classes for testing
    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        mapper = mock(StudentMapper.class);
        service = new StudentService(repository, mapper);
    }
    @Test
    @DisplayName("Test same email exception")
    void create_throwsIfEmailExists() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setEmail("existing.email@example.com");
        request.setName("Test Name");
        request.setPassword("secret");

        // when/then mockito behavior: https://www.baeldung.com/mockito-behavior
        // basically tells to mockito to do something when something happens instead of the default behavior
        // -> mocks real code behavior but do not initiate any object creation etc
        when(repository.existsByEmailIgnoreCase("existing.email@example.com")).thenReturn(true);

        //asserting BadRequestException when trying to create duplicate email user
        assertThrows(BadRequestException.class, () -> service.create(request));

        // verify method tests if the repo object were called. If not -> false test result
        // -> fails the test instead that gives false results.
        verify(repository, never()).save(any());
    }
    @Test
    @DisplayName("Successfull creation")
    void create_savesEntity_returnsDto() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setEmail("new.guy@example.com");
        request.setName("New Guy");
        request.setPassword("kissa123");

        Student studentEntity = new Student("New Guy", "new.guy@example.com", "kissa123");
        Student createdStudent = new Student("New Guy", "new.guy@example.com", "kissa123");
        StudentDto studentDto = new StudentDto(1, "New Guy", "new.guy@example.com");

        //making sure exception doesnt fail the test
        when(repository.existsByEmailIgnoreCase("new.guy@example.com")).thenReturn(false);

        //making sure we get correct creation in mock tests
        when(mapper.toStudentEntity(request)).thenReturn(studentEntity);
        when(repository.save(studentEntity)).thenReturn(createdStudent);
        when(mapper.toStudentDto(createdStudent)).thenReturn(studentDto);

        StudentDto response = service.create(request);

        assertEquals(1, response.getStudentID());
        verify(repository).save(studentEntity);
    }

    @Test
    @DisplayName("Successfull read")
    void read() {
        when(repository.existsByStudentID(5)).thenReturn(true);

        Student student = new Student("Name", "test@test.fi", "word");
        when(repository.getReferenceById((long) 5)).thenReturn(student);

        StudentDto studentDto = new StudentDto(5, "Name", "test@test.fi");
        when(mapper.toStudentDto(student)).thenReturn(studentDto);

        StudentDto res = service.read(5);

        assertEquals("Name", res.getName());
        verify(repository).getReferenceById((long) 5);
    }

    @Test
    @DisplayName("Successfull update")
    void update() {
        Student existing = new Student("Old", "old@example.com", "pw");
        // optional is more controllable null (IDEA provided this info)
        when(repository.findById((long) 5)).thenReturn(Optional.of(existing));

        UpdateStudentRequest request = new UpdateStudentRequest();
        StudentDto studentDto = new StudentDto(5, "New", "old@example.com");
        when(mapper.toStudentDto(existing)).thenReturn(studentDto);

        StudentDto res = service.update(5, request);

        verify(mapper).updateStudentEntity(existing, request);
        verify(repository).save(existing);
        assertEquals(5, res.getStudentID());
    }

    @Test
    @DisplayName("Succesfull deletion")
    void delete() {
        Student existing = new Student("namename", "namename@metropolia.fi", "veripassword");
        when(repository.findById((long) 5)).thenReturn(Optional.of(existing));

        service.delete(5);

        verify(repository).delete(existing);
    }
}