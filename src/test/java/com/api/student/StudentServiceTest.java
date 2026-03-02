package com.api.student;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.NotFoundException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.login.Auth;
import com.api.login.LoginService;
import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.student.dto.UpdateStudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository repository;
    private StudentMapper mapper;
    private StudentService service;
    private LoginService loginService;
    private Auth auth;

    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        mapper = mock(StudentMapper.class);
        loginService = mock(LoginService.class);
        auth = mock(Auth.class);

        service = new StudentService(repository, mapper, loginService);
    }

    @Test
    @DisplayName("create() throws if not admin")
    void create_throwsIfNotAdmin() {

        when(auth.getRole()).thenReturn("teacher");

        CreateStudentRequest request = new CreateStudentRequest();

        assertThrows(UnauthorizedException.class,
                () -> service.create(request, auth));
    }

    @Test
    @DisplayName("create() throws if email exists")
    void create_throwsIfEmailExists() {

        when(auth.getRole()).thenReturn("admin");

        CreateStudentRequest request = new CreateStudentRequest();
        request.setEmail("existing@email.com");

        when(loginService.isEmailAvailable("existing@email.com"))
                .thenReturn(false);

        assertThrows(BadRequestException.class,
                () -> service.create(request, auth));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("create() saves and returns DTO")
    void create_savesEntity_returnsDto() {

        when(auth.getRole()).thenReturn("admin");

        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("New");
        request.setLastName("Guy");
        request.setEmail("new@example.com");
        request.setPassword("pw");

        Student entity = new Student("New", "Guy", "new@example.com", "pw");
        Student saved = new Student("New", "Guy", "new@example.com", "pw");

        StudentDto dto = new StudentDto(1, "New", "Guy", "new@example.com");

        when(loginService.isEmailAvailable("new@example.com"))
                .thenReturn(true);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        StudentDto result = service.create(request, auth);

        assertEquals(1, result.getStudentID());
        assertEquals("New", result.getFirstName());
        assertEquals("Guy", result.getLastName());
        assertEquals("new@example.com", result.getEmail());

        verify(repository).save(entity);
    }

    @Test
    @DisplayName("read() allows admin")
    void read_allowsAdmin() {

        when(auth.getRole()).thenReturn("admin");

        Student student = new Student("Name", "Surname", "test@test.fi", "pw");

        when(repository.findById(5)).thenReturn(Optional.of(student));
        when(mapper.toDto(student))
                .thenReturn(new StudentDto(5, "Name", "Surname", "test@test.fi"));

        StudentDto res = service.read(5, auth);

        assertEquals("Name", res.getFirstName());
    }

    @Test
    @DisplayName("read() allows self student")
    void read_allowsSelf() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("self@test.fi");

        Student student = new Student("Self", "Student", "self@test.fi", "pw");

        when(repository.findById(1)).thenReturn(Optional.of(student));
        when(mapper.toDto(student))
                .thenReturn(new StudentDto(1, "Self", "Student", "self@test.fi"));

        StudentDto res = service.read(1, auth);

        assertEquals("Self", res.getFirstName());
    }

    @Test
    @DisplayName("read() throws if student tries to read someone else")
    void read_throwsIfUnauthorizedStudent() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("wrong@test.fi");

        Student student = new Student("Name", "Surname", "real@test.fi", "pw");

        when(repository.findById(1)).thenReturn(Optional.of(student));

        assertThrows(UnauthorizedException.class,
                () -> service.read(1, auth));
    }

    @Test
    @DisplayName("read() throws if not found")
    void read_throwsIfNotFound() {

        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.read(99, auth));
    }

    @Test
    @DisplayName("readAll() allows admin")
    void readAll_admin() {

        when(auth.getRole()).thenReturn("admin");

        when(repository.findAll()).thenReturn(List.of());
        when(mapper.toDto(any())).thenReturn(null);

        assertDoesNotThrow(() -> service.readAll(auth));
    }

    @Test
    @DisplayName("readAll() throws if student")
    void readAll_throwsIfStudent() {

        when(auth.getRole()).thenReturn("student");

        assertThrows(UnauthorizedException.class,
                () -> service.readAll(auth));
    }

    @Test
    @DisplayName("update() allows admin")
    void update_allowsAdmin() {

        when(auth.getRole()).thenReturn("admin");

        Student existing = new Student("Old", "Student", "old@example.com", "pw");

        when(repository.findById(5)).thenReturn(Optional.of(existing));
        when(mapper.toDto(existing))
                .thenReturn(new StudentDto(5, "Old", "Student", "old@example.com"));

        UpdateStudentRequest request = new UpdateStudentRequest();

        StudentDto res = service.update(5, request, auth);

        verify(mapper).updateEntity(existing, request);
        verify(repository).save(existing);
        assertEquals(5, res.getStudentID());
    }

    @Test
    @DisplayName("update() allows self")
    void update_allowsSelf() {

        when(auth.getRole()).thenReturn("student");
        when(auth.getEmail()).thenReturn("self@test.fi");

        Student existing = new Student("Self", "Student", "self@test.fi", "pw");

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(mapper.toDto(existing))
                .thenReturn(new StudentDto(1, "Self", "Student", "self@test.fi"));

        UpdateStudentRequest request = new UpdateStudentRequest();

        assertDoesNotThrow(() -> service.update(1, request, auth));
    }

    @Test
    @DisplayName("update() throws if unauthorized")
    void update_throwsIfUnauthorized() {

        when(auth.getRole()).thenReturn("teacher");
        when(auth.getEmail()).thenReturn("teacher@test.fi");

        Student existing = new Student("Name", "Surname", "real@test.fi", "pw");

        when(repository.findById(1)).thenReturn(Optional.of(existing));

        UpdateStudentRequest request = new UpdateStudentRequest();

        assertThrows(UnauthorizedException.class,
                () -> service.update(1, request, auth));
    }

    @Test
    @DisplayName("delete() allows admin")
    void delete_allowsAdmin() {

        when(auth.getRole()).thenReturn("admin");

        Student existing = new Student("Name", "Surname", "mail@test.fi", "pw");

        when(repository.findById(5)).thenReturn(Optional.of(existing));

        service.delete(5, auth);

        verify(repository).delete(existing);
    }

    @Test
    @DisplayName("delete() throws if not admin")
    void delete_throwsIfNotAdmin() {

        when(auth.getRole()).thenReturn("teacher");

        assertThrows(UnauthorizedException.class,
                () -> service.delete(5, auth));
    }

    @Test
    @DisplayName("delete() throws if not found")
    void delete_throwsIfNotFound() {

        when(auth.getRole()).thenReturn("admin");
        when(repository.findById(5)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> service.delete(5, auth));
    }
}