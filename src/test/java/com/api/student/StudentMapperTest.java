package com.api.student;

import com.api.common.util.HashUtils;
import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.student.dto.UpdateStudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    @DisplayName("Test for toStudentEntity() method does not mutate values")
    void toEntity() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("Test");
        request.setLastName("Name");
        request.setEmail("test.email@email.com");
        request.setPassword("testPassword");

        Student student = mapper.toEntity(request);

        assertAll(
                () -> assertEquals("Test", student.getFirstName()),
                () -> assertEquals("Name", student.getLastName()),
                () -> assertEquals("test.email@email.com", student.getEmail()),
                () -> assertTrue(HashUtils.check("testPassword", student.getPassword()))
        );
    }

    @Test
    @DisplayName("Tests for update method in student mapper, partially, completely and if no updates it wont crash")
    void updateEntity() {
        Student student = new Student("Old", "Name",
                "old.email@email.com",
                "oldpassword");

        UpdateStudentRequest requestPartial = new UpdateStudentRequest();
        UpdateStudentRequest requestCompletely = new UpdateStudentRequest();
        UpdateStudentRequest requestNoChange = new UpdateStudentRequest();

        requestPartial.setFirstName("New");
        requestPartial.setEmail("new.email@email.com");

        requestCompletely.setFirstName("Complete");
        requestCompletely.setLastName("Update");
        requestCompletely.setEmail("complete@email.com");
        requestCompletely.setPassword("Complete");

        mapper.updateEntity(student, requestPartial);

        assertAll(
                () -> assertEquals("New", student.getFirstName()),
                () -> assertEquals("Name", student.getLastName()), // unchanged
                () -> assertEquals("new.email@email.com", student.getEmail()),
                () -> assertEquals("oldpassword", student.getPassword()) // unchanged
        );

        mapper.updateEntity(student, requestCompletely);

        assertAll(
                () -> assertEquals("Complete", student.getFirstName()),
                () -> assertEquals("Update", student.getLastName()),
                () -> assertEquals("complete@email.com", student.getEmail()),
                () -> assertTrue(HashUtils.check("Complete", student.getPassword()))
        );

        mapper.updateEntity(student, requestNoChange);

        assertAll(
                () -> assertEquals("Complete", student.getFirstName()),
                () -> assertEquals("Update", student.getLastName()),
                () -> assertEquals("complete@email.com", student.getEmail()),
                () -> assertTrue(HashUtils.check("Complete", student.getPassword()))
        );
    }

    @Test
    @DisplayName("toStudentDto mapping test")
    void toDto() {
        Student student = new Student("Name", "Surname", "email@example.com", "pw");
        StudentDto studentDto = mapper.toDto(student);

        assertAll(
                () -> assertEquals("Name", studentDto.getFirstName()),
                () -> assertEquals("Surname", studentDto.getLastName()),
                () -> assertEquals("email@example.com", studentDto.getEmail())
        );
    }
}
