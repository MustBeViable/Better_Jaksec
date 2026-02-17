package com.api.teacher;

import com.api.common.error.exceptions.BadRequestException;
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

    @BeforeEach
    void setUp() {
        repository = mock(TeacherRepository.class);
        mapper = mock(TeacherMapper.class);
        service = new TeacherService(repository, mapper);
    }

    @Test
    @DisplayName("create throws if email already exists")
    void create_throwsIfEmailExists() {
        CreateTeacherRequest request = new CreateTeacherRequest();
        request.setFirstName("Test");
        request.setLastName("Teacher");
        request.setEmail("existing@example.com");
        request.setPassword("secret");

        when(repository.existsByEmailIgnoreCase("existing@example.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> service.create(request));

        verify(repository, never()).save(any());
        verify(mapper, never()).toEntity(any());
    }

    @Test
    @DisplayName("create saves entity and returns dto")
    void create_savesEntity_returnsDto() {
        CreateTeacherRequest request = new CreateTeacherRequest();
        request.setFirstName("New");
        request.setLastName("Guy");
        request.setEmail("new.guy@example.com");
        request.setPassword("pw");

        Teacher entity = new Teacher();
        entity.setFirstName("New");
        entity.setLastName("Guy");
        entity.setEmail("new.guy@example.com");
        entity.setPassword("pw");

        Teacher saved = new Teacher();
        saved.setTeacherID(1);
        saved.setFirstName("New");
        saved.setLastName("Guy");
        saved.setEmail("new.guy@example.com");
        saved.setPassword("pw");

        TeacherDto dto = new TeacherDto(1, "New", "Guy", "new.guy@example.com", null);

        when(repository.existsByEmailIgnoreCase("new.guy@example.com")).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        TeacherDto response = service.create(request);

        assertAll(
                () -> assertEquals(1, response.getTeacherID()),
                () -> assertEquals("New", response.getFirstName()),
                () -> assertEquals("Guy", response.getLastName()),
                () -> assertEquals("new.guy@example.com", response.getEmail())
        );

        verify(repository).save(entity);
    }

    @Test
    @DisplayName("read returns dto when teacher exists")
    void read() {
        when(repository.existsByTeacherID(5)).thenReturn(true);

        Teacher teacher = new Teacher();
        teacher.setTeacherID(5);
        teacher.setFirstName("A");
        teacher.setLastName("B");
        teacher.setEmail("a@b.com");
        teacher.setPassword("pw");

        when(repository.getReferenceById(5)).thenReturn(teacher);

        TeacherDto dto = new TeacherDto(5, "A", "B", "a@b.com", null);
        when(mapper.toDto(teacher)).thenReturn(dto);

        TeacherDto res = service.read(5);

        assertAll(
                () -> assertEquals(5, res.getTeacherID()),
                () -> assertEquals("A", res.getFirstName()),
                () -> assertEquals("B", res.getLastName()),
                () -> assertEquals("a@b.com", res.getEmail())
        );

        verify(repository).getReferenceById(5);
    }

    @Test
    @DisplayName("update updates teacher and returns dto")
    void update() {
        Teacher existing = new Teacher();
        existing.setTeacherID(5);
        existing.setFirstName("Old");
        existing.setLastName("Teacher");
        existing.setEmail("old@x.com");
        existing.setPassword("pw");

        when(repository.findById(5)).thenReturn(Optional.of(existing));

        UpdateTeacherRequest request = new UpdateTeacherRequest();
        request.setFirstName("New");

        TeacherDto dto = new TeacherDto(5, "New", "Teacher", "old@x.com", null);
        when(mapper.toDto(existing)).thenReturn(dto);

        TeacherDto res = service.update(5, request);

        verify(mapper).updateEntity(existing, request);
        verify(repository).save(existing);

        assertEquals(5, res.getTeacherID());
    }

    @Test
    @DisplayName("delete removes teacher")
    void delete() {
        Teacher existing = new Teacher();
        existing.setTeacherID(5);

        when(repository.findById(5)).thenReturn(Optional.of(existing));

        service.delete(5);

        verify(repository).delete(existing);
    }
}
