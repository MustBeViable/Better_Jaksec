package com.api.student;

import com.api.student.dto.CreateStudentRequest;
import com.api.student.dto.StudentDto;
import com.api.student.dto.UpdateStudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    @DisplayName("Test for toStudentEntity() method does not mutate values")
    void toStudentEntity() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setName("Test name");
        request.setEmail("Test.email@email.com");
        request.setPassword("testPassword");

        Student student = mapper.toStudentEntity(request);

        assertAll(
                () -> assertEquals("Test name", student.getName()),
                () -> assertEquals("Test.email@email.com", student.getEmail()),
                () -> assertEquals("testPassword", student.getPassword())
                );
    }

    @Test
    @DisplayName("Tests for update method in student mapper, partially, completely and if no updates it wont crash")
    void updateStudentEntity() {
        Student student = new Student("Old name",
                "old.email@email.com",
                "oldpassword");

        UpdateStudentRequest requestPartial = new UpdateStudentRequest();
        UpdateStudentRequest requestCompletely = new UpdateStudentRequest();
        UpdateStudentRequest requestNoChange = new UpdateStudentRequest();

        requestPartial.setName("New name");
        requestPartial.setEmail("new.email@email.com");

        requestCompletely.setName("Complete");
        requestCompletely.setEmail("complete@email.com");
        requestCompletely.setPassword("Complete");

        mapper.updateStudentEntity(student, requestPartial);

        assertAll(
                () -> assertEquals("New name", student.getName()),
                () -> assertEquals("new.email@email.com", student.getEmail()),
                () -> assertEquals("oldpassword", student.getPassword())
        );

        mapper.updateStudentEntity(student, requestCompletely);

        assertAll(
                () -> assertEquals("Complete", student.getName()),
                () -> assertEquals("complete@email.com", student.getEmail()),
                () -> assertEquals("Complete", student.getPassword())
        );

        mapper.updateStudentEntity(student, requestNoChange);

        assertAll(
                () -> assertEquals("Complete", student.getName()),
                () -> assertEquals("complete@email.com", student.getEmail()),
                () -> assertEquals("Complete", student.getPassword())
        );

    }

    @Test
    @DisplayName("toStudentDto mapping test")
    void toStudentDto() {
        Student student = new Student("Name", "email@example.com", "pw");
        StudentDto studentDto = mapper.toStudentDto(student);

        assertAll(
                () -> assertEquals("Name", studentDto.getName()),
                () -> assertEquals("email@example.com", studentDto.getEmail())
        );
    }
}