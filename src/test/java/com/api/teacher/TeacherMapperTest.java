package com.api.teacher;

import com.api.teacher.dto.CreateTeacherRequest;
import com.api.teacher.dto.TeacherDto;
import com.api.teacher.dto.UpdateTeacherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherMapperTest {

    private TeacherMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TeacherMapper();
    }

    @Test
    @DisplayName("toTeacherEntity maps CreateTeacherRequest -> Teacher without mutating values")
    void toEntity() {
        CreateTeacherRequest request = new CreateTeacherRequest();
        request.setFirstName("Test");
        request.setLastName("Teacher");
        request.setEmail("test.teacher@email.com");
        request.setPassword("secret");

        Teacher teacher = mapper.toEntity(request);

        assertAll(
                () -> assertEquals("Test", teacher.getFirstName()),
                () -> assertEquals("Teacher", teacher.getLastName()),
                () -> assertEquals("test.teacher@email.com", teacher.getEmail()),
                () -> assertEquals("secret", teacher.getPassword())
        );
    }

    @Test
    @DisplayName("updateTeacherEntity supports partial + full + no-change updates safely")
    void updateEntity() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Old");
        teacher.setLastName("Name");
        teacher.setEmail("old@email.com");
        teacher.setPassword("oldpw");

        UpdateTeacherRequest partial = new UpdateTeacherRequest();
        partial.setFirstName("New");
        partial.setEmail("new@email.com");

        mapper.updateEntity(teacher, partial);

        assertAll(
                () -> assertEquals("New", teacher.getFirstName()),
                () -> assertEquals("Name", teacher.getLastName()), // unchanged
                () -> assertEquals("new@email.com", teacher.getEmail()),
                () -> assertEquals("oldpw", teacher.getPassword()) // unchanged
        );

        UpdateTeacherRequest full = new UpdateTeacherRequest();
        full.setFirstName("Full");
        full.setLastName("Update");
        full.setEmail("full@email.com");
        full.setPassword("newpw");

        mapper.updateEntity(teacher, full);

        assertAll(
                () -> assertEquals("Full", teacher.getFirstName()),
                () -> assertEquals("Update", teacher.getLastName()),
                () -> assertEquals("full@email.com", teacher.getEmail()),
                () -> assertEquals("newpw", teacher.getPassword())
        );

        UpdateTeacherRequest noChange = new UpdateTeacherRequest();
        mapper.updateEntity(teacher, noChange);

        assertAll(
                () -> assertEquals("Full", teacher.getFirstName()),
                () -> assertEquals("Update", teacher.getLastName()),
                () -> assertEquals("full@email.com", teacher.getEmail()),
                () -> assertEquals("newpw", teacher.getPassword())
        );
    }

    @Test
    @DisplayName("toTeacherDto maps Teacher -> TeacherDto")
    void toDto() {
        Teacher teacher = new Teacher();
        teacher.setTeacherID(7);
        teacher.setFirstName("Ada");
        teacher.setLastName("Lovelace");
        teacher.setEmail("ada@history.com");
        teacher.setPassword("pw"); // should not leak to DTO

        TeacherDto dto = mapper.toDto(teacher);

        assertAll(
                () -> assertEquals(7, dto.getTeacherID()),
                () -> assertEquals("Ada", dto.getFirstName()),
                () -> assertEquals("Lovelace", dto.getLastName()),
                () -> assertEquals("ada@history.com", dto.getEmail())
        );
    }
}
