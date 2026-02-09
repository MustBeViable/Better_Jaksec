package com.api.course;

import static org.junit.jupiter.api.Assertions.*;
import com.api.assignment.Assignment;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.Lesson;
import com.api.teacher.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseMapperTest {

    private CourseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CourseMapper();
    }

    @Test
    @DisplayName("toEmptyCourseEntity() sets courseName and returns empty entity")
    void toEmptyCourseEntitySetsNameOnly() {
        CreateCourseRequest request = new CreateCourseRequest();
        request.setCourseName("Math 101");

        Course course = mapper.toEmptyCourseEntity(request);

        assertAll(
                () -> assertEquals("Math 101", course.getCourseName()),
                () -> assertNull(course.getCourseID()),
                () -> assertTrue(course.getLessons() == null || course.getLessons().isEmpty()),
                () -> assertTrue(course.getAssignments() == null || course.getAssignments().isEmpty()),
                () -> assertTrue(course.getTeachers() == null || course.getTeachers().isEmpty())
        );
    }

    @Test
    @DisplayName("toCourseEntity() sets courseName and returns entity")
    void toCourseEntitySetsNameOnly() {
        CreateCourseRequest request = new CreateCourseRequest();
        request.setCourseName("Physics 101");

        Course course = mapper.toCourseEntity(request);

        assertEquals("Physics 101", course.getCourseName());
        assertNull(course.getCourseID());
    }

    @Test
    @DisplayName("updateCourseEntity() updates only provided fields")
    void updateCourseEntityPartialUpdate() {
        Course course = new Course();
        course.setCourseName("Original Name");

        UpdateCourseRequest request = new UpdateCourseRequest();
        request.setCourseName("Updated Name");

        mapper.updateCourseEntity(course, request);

        assertEquals("Updated Name", course.getCourseName());
    }

    @Test
    @DisplayName("updateCourseEntity() with null fields does not change anything")
    void updateCourseEntityEmptyRequest() {
        Course course = new Course();
        course.setCourseName("Original Name");

        UpdateCourseRequest request = new UpdateCourseRequest();

        assertDoesNotThrow(() -> mapper.updateCourseEntity(course, request));
        assertEquals("Original Name", course.getCourseName());
    }

    @Test
    @DisplayName("toCourseDto() maps all relations correctly")
    void toCourseDtoMapsAllRelations() {
        Course course = new Course();
        course.setCourseID(1L);
        course.setCourseName("History 101");

        Lesson l1 = new Lesson(); l1.setLessonID(10L);
        Lesson l2 = new Lesson(); l2.setLessonID(20L);
        course.setLessons(new HashSet<>(Set.of(l1, l2)));

        Assignment a1 = new Assignment(); a1.setAssignmentID(100L);
        Assignment a2 = new Assignment(); a2.setAssignmentID(200L);
        course.setAssignments(new HashSet<>(Set.of(a1, a2)));

        Teacher t1 = new Teacher();
        t1.setTeacherID(1000);
        t1.setFirstName("John");
        t1.setLastName("Doe");

        Teacher t2 = new Teacher();
        t2.setTeacherID(2000);
        t2.setFirstName("Jane");
        t2.setLastName("Smith");

        course.setTeachers(new HashSet<>(Set.of(t1, t2)));

        CourseDto dto = mapper.toCourseDto(course);

        assertAll(
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("History 101", dto.getName()),
                () -> assertEquals(Set.of(10L, 20L), dto.getLessonIds()),
                () -> assertEquals(Set.of(100L, 200L), dto.getAssignmentIds()),
                () -> assertEquals(Set.of("John Doe", "Jane Smith"), dto.getTeacherNames())
        );
    }
}