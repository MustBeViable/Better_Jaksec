package com.api.teacher;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.NotFoundException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.login.Auth;
import com.api.login.LoginService;
import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    private TeacherRepository repository;
    private TeacherMapper mapper;
    private TeacherService service;
    private LoginService loginService;
    private Auth auth;

    @BeforeEach
    void setUp() {
        repository = mock(TeacherRepository.class);
        mapper = mock(TeacherMapper.class);
        loginService = mock(LoginService.class);
        auth = mock(Auth.class);
        service = new TeacherService(repository, mapper, loginService);
    }

    @Test
    @DisplayName("create() throws if email exists")
    void create_throwsIfEmailExists() {
        when(auth.getRole()).thenReturn("admin");

        CreateTeacherRequest request = new CreateTeacherRequest();
        request.setEmail("existing@example.com");

        when(loginService.isEmailAvailable("existing@example.com")).thenReturn(false);

        assertThrows(BadRequestException.class,
                () -> service.create(request, auth));

        verify(repository, never()).save(any());
        verify(mapper, never()).toEntity(any());
    }

    @Test
    @DisplayName("create() throws if not admin")
    void create_throwsIfUnauthorized() {
        when(auth.getRole()).thenReturn("teacher");

        CreateTeacherRequest request = new CreateTeacherRequest();
        request.setEmail("new@example.com");

        assertThrows(UnauthorizedException.class,
                () -> service.create(request, auth));
    }

    @Test
    @DisplayName("create() saves entity and returns dto")
    void create_savesEntity_returnsDto() {
        when(auth.getRole()).thenReturn("admin");

        CreateTeacherRequest request = new CreateTeacherRequest();
        request.setFirstName("New");
        request.setLastName("Guy");
        request.setEmail("new.guy@example.com");
        request.setPassword("pw");

        Teacher entity = new Teacher();
        Teacher saved = new Teacher();
        saved.setTeacherID(1);

        TeacherDto dto = new TeacherDto(1, "New", "Guy", "new.guy@example.com", null);

        when(loginService.isEmailAvailable("new.guy@example.com")).thenReturn(true);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        TeacherDto result = service.create(request, auth);

        assertEquals(1, result.getTeacherID());
        assertEquals("new.guy@example.com", result.getEmail());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("read() returns dto when teacher exists")
    void read_returnsDto() {
        when(auth.getRole()).thenReturn("admin");

        Teacher teacher = new Teacher();
        teacher.setTeacherID(5);
        teacher.setEmail("a@b.com");

        TeacherDto dto = new TeacherDto(5, "A", "B", "a@b.com", null);

        when(repository.findById(5)).thenReturn(Optional.of(teacher));
        when(mapper.toDto(teacher)).thenReturn(dto);

        TeacherDto res = service.read(5, auth);

        assertEquals(5, res.getTeacherID());
        verify(repository).findById(5);
    }

    @Test
    @DisplayName("read() throws if teacher not found")
    void read_throwsIfNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> service.read(99, auth));
    }

    @Test
    @DisplayName("update() allows admin")
    void update_allowsAdmin() {
        when(auth.getRole()).thenReturn("admin");

        Teacher existing = new Teacher();
        existing.setTeacherID(5);
        existing.setEmail("old@x.com");

        UpdateTeacherRequest request = new UpdateTeacherRequest();
        request.setFirstName("New");

        TeacherDto dto = new TeacherDto(5, "New", "Teacher", "old@x.com", null);

        when(repository.findById(5)).thenReturn(Optional.of(existing));
        when(mapper.toDto(existing)).thenReturn(dto);

        TeacherDto result = service.update(5, request, auth);

        verify(mapper).updateEntity(existing, request);
        verify(repository).save(existing);
        assertEquals(5, result.getTeacherID());
    }

    @Test
    @DisplayName("update() allows self")
    void update_allowsSelf() {
        when(auth.getRole()).thenReturn("teacher");
        when(auth.getEmail()).thenReturn("old@x.com");

        Teacher existing = new Teacher();
        existing.setTeacherID(5);
        existing.setEmail("old@x.com");

        UpdateTeacherRequest request = new UpdateTeacherRequest();
        request.setFirstName("New");

        TeacherDto dto = new TeacherDto(5, "New", "Teacher", "old@x.com", null);

        when(repository.findById(5)).thenReturn(Optional.of(existing));
        when(mapper.toDto(existing)).thenReturn(dto);

        TeacherDto result = service.update(5, request, auth);

        verify(mapper).updateEntity(existing, request);
        verify(repository).save(existing);
        assertEquals(5, result.getTeacherID());
    }

    @Test
    @DisplayName("update() throws if not admin or self")
    void update_throwsIfUnauthorized() {
        when(auth.getRole()).thenReturn("teacher");
        when(auth.getEmail()).thenReturn("other@x.com");

        UpdateTeacherRequest request = new UpdateTeacherRequest();
        Teacher existing = new Teacher();
        existing.setTeacherID(5);
        existing.setEmail("old@x.com");

        when(repository.findById(5)).thenReturn(Optional.of(existing));

        assertThrows(UnauthorizedException.class,
                () -> service.update(5, request, auth));
    }

    @Test
    @DisplayName("update() throws if teacher not found")
    void update_throwsIfNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        UpdateTeacherRequest request = new UpdateTeacherRequest();

        assertThrows(BadRequestException.class,
                () -> service.update(99, request, auth));
    }

    @Test
    @DisplayName("delete() allows admin")
    void delete_allowsAdmin() {
        when(auth.getRole()).thenReturn("admin");
        when(auth.getEmail()).thenReturn("admin@x.com");

        Teacher teacher = new Teacher();
        teacher.setTeacherID(5);
        teacher.setEmail("other@x.com");

        when(repository.findById(5)).thenReturn(Optional.of(teacher));

        service.delete(5, auth);

        verify(repository).delete(teacher);
    }

    @Test
    @DisplayName("delete() throws if admin tries to delete self")
    void delete_throwsIfAdminDeletesSelf() {
        when(auth.getRole()).thenReturn("admin");
        when(auth.getEmail()).thenReturn("admin@x.com");

        Teacher teacher = new Teacher();
        teacher.setTeacherID(5);
        teacher.setEmail("admin@x.com");

        when(repository.findById(5)).thenReturn(Optional.of(teacher));

        assertThrows(UnauthorizedException.class,
                () -> service.delete(5, auth));
    }

    @Test
    @DisplayName("delete() throws if teacher not found")
    void delete_throwsIfNotFound() {
        when(auth.getRole()).thenReturn("admin");
        when(auth.getEmail()).thenReturn("admin@x.com");

        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class,
                () -> service.delete(99, auth));
    }
}