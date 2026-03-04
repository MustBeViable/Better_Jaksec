package com.api.jointable.student_lesson;

import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.lesson.Lesson;
import com.api.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class StudentLessonMapperTest {

    private StudentLessonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentLessonMapper();
    }

    @Test
    @DisplayName("toEntity() maps values correctly")
    void toEntity() {

        CreateStudentLesson request = new CreateStudentLesson();
        request.setPresent(true);
        request.setReason("Sick");

        StudentLesson entity = mapper.toEntity(request);

        assertAll(
                () -> assertTrue(entity.isPresent()),
                () -> assertEquals("Sick", entity.getReasonForAbsence())
        );
    }

    @Test
    @DisplayName("updateEntity() updates partially and fully")
    void updateEntity() {

        StudentLesson entity = new StudentLesson();
        entity.setPresent(false);
        entity.setReasonForAbsence("Old");

        UpdateStudentLesson partial = new UpdateStudentLesson();
        partial.setPresent(true);

        mapper.updateEntity(entity, partial);

        assertAll(
                () -> assertTrue(entity.isPresent()),
                () -> assertEquals("Old", entity.getReasonForAbsence())
        );

        UpdateStudentLesson full = new UpdateStudentLesson();
        full.setPresent(false);
        full.setReason("Updated");

        mapper.updateEntity(entity, full);

        assertAll(
                () -> assertFalse(entity.isPresent()),
                () -> assertEquals("Updated", entity.getReasonForAbsence())
        );
    }

    @Test
    @DisplayName("toDto() maps entity to DTO correctly")
    void toDto() {

        Student student = new Student();
        student.setStudentID(5);

        Lesson lesson = new Lesson();
        lesson.setLessonID(10L);
        lesson.setDate(Instant.now());

        StudentLesson entity = new StudentLesson();
        entity.setStudent(student);
        entity.setLesson(lesson);
        entity.setPresent(true);

        StudentLessonDto dto = mapper.toDto(entity);

        assertAll(
                () -> assertEquals(5, dto.getStudentId()),
                () -> assertEquals(10L, dto.getLessonId()),
                () -> assertTrue(dto.getPresent())
        );
    }
}