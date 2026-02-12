package com.api.student;

import com.api.common.error.exceptions.BadRequestException;
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
        request.setFirstName("Test");
        request.setLastName("Name");
        request.setEmail("existing.email@example.com");
        request.setPassword("secret");

        when(repository.existsByEmailIgnoreCase("existing.email@example.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> service.create(request));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Successfull creation")
    void create_savesEntity_returnsDto() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("New");
        request.setLastName("Guy");
        request.setEmail("new.guy@example.com");
        request.setPassword("kissa123");

        Student studentEntity = new Student("New", "Guy", "new.guy@example.com", "kissa123");
        Student createdStudent = new Student("New", "Guy", "new.guy@example.com", "kissa123");
        StudentDto studentDto = new StudentDto(1, "New", "Guy", "new.guy@example.com");

        when(repository.existsByEmailIgnoreCase("new.guy@example.com")).thenReturn(false);

        when(mapper.toStudentEntity(request)).thenReturn(studentEntity);
        when(repository.save(studentEntity)).thenReturn(createdStudent);
        when(mapper.toStudentDto(createdStudent)).thenReturn(studentDto);

        StudentDto response = service.create(request);

        assertEquals(1, response.getStudentID());
        assertEquals("New", response.getFirstName());
        assertEquals("Guy", response.getLastName());
        assertEquals("new.guy@example.com", response.getEmail());
        verify(repository).save(studentEntity);
    }

    @Test
    @DisplayName("Successfull read")
    void read() {
        when(repository.existsByStudentID(5)).thenReturn(true);

        Student student = new Student("Name", "Surname", "test@test.fi", "word");
        when(repository.getReferenceById(5)).thenReturn(student);

        StudentDto studentDto = new StudentDto(5, "Name", "Surname", "test@test.fi");
        when(mapper.toStudentDto(student)).thenReturn(studentDto);

        StudentDto res = service.read(5);

        assertEquals("Name", res.getFirstName());
        assertEquals("Surname", res.getLastName());
        verify(repository).getReferenceById(5);
    }

    @Test
    @DisplayName("Successfull update")
    void update() {
        Student existing = new Student("Old", "Student", "old@example.com", "pw");
        when(repository.findById(5)).thenReturn(Optional.of(existing));

        UpdateStudentRequest request = new UpdateStudentRequest();

        StudentDto studentDto = new StudentDto(5, "Old", "Student", "old@example.com");
        when(mapper.toStudentDto(existing)).thenReturn(studentDto);

        StudentDto res = service.update(5, request);

        verify(mapper).updateStudentEntity(existing, request);
        verify(repository).save(existing);
        assertEquals(5, res.getStudentID());
    }

    @Test
    @DisplayName("Succesfull deletion")
    void delete() {
        Student existing = new Student("name", "name", "namename@metropolia.fi", "veripassword");
        when(repository.findById(5)).thenReturn(Optional.of(existing));

        service.delete(5);

        verify(repository).delete(existing);
    }
}
