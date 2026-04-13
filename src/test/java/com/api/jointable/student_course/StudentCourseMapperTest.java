package com.api.jointable.student_course;

import com.api.course.Course;
import com.api.jointable.student_course.dto.CreateStudentCourse;
import com.api.jointable.student_course.dto.StudentCourseDto;
import com.api.jointable.student_course.dto.UpdateStudentCourse;
import com.api.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentCourseMapperTest {

    private StudentCourseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentCourseMapper();
    }

    @Test
    @DisplayName("toEntity() maps values correctly")
    void toEntity() {
        CreateStudentCourse request = new CreateStudentCourse();
        request.setGrade(5);

        StudentCourse entity = mapper.toEntity(request);

        assertEquals(5, entity.getGrade());
    }

    @Test
    @DisplayName("updateEntity() updates grade, including null")
    void updateEntity() {
        StudentCourse entity = new StudentCourse();
        entity.setGrade(1);

        UpdateStudentCourse update = new UpdateStudentCourse();
        update.setGrade(3);

        mapper.updateEntity(entity, update);
        assertEquals(3, entity.getGrade());

        UpdateStudentCourse clearGrade = new UpdateStudentCourse();
        clearGrade.setGrade(null);

        mapper.updateEntity(entity, clearGrade);
        assertNull(entity.getGrade());
    }

    @Test
    @DisplayName("toDto() maps entity to DTO correctly")
    void toDto() {
        Student student = new Student();
        student.setStudentID(10);

        Course course = new Course();
        course.setCourseID(20L);

        StudentCourse entity = new StudentCourse();
        entity.setId(99L);
        entity.setStudent(student);
        entity.setCourse(course);
        entity.setGrade(4);

        StudentCourseDto dto = mapper.toDto(entity);

        assertAll(
                () -> assertEquals(99L, dto.getGradeId()),
                () -> assertEquals(10, dto.getStudentId()),
                () -> assertEquals(20L, dto.getCourseId()),
                () -> assertEquals(4, dto.getGrade())
        );
    }
}