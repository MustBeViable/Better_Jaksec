package com.api;

import com.api.course.Course;
import com.api.assignment.Assignment;
import com.api.jointable.StudentLesson;
import com.api.lesson.Lesson;
import com.api.teacher.Teacher;
import com.api.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AI generated, just to test database mappings
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRelationshipTest {

    @Autowired
    private TestEntityManager em;

    // -------------------------
    // Student entity
    // -------------------------
    @Test
    void createStudent() {
        Student student = new Student();
        student.setFirstName("Alice");
        student.setLastName("Wonderland");
        student.setEmail("alice@student.com");
        student.setPassword("pw123");
        em.persist(student);
        em.flush();
        em.clear();

        Student found = em.find(Student.class, student.getStudentID());
        assertEquals("Alice", found.getFirstName());
        assertEquals("Wonderland", found.getLastName());
        assertEquals("alice@student.com", found.getEmail());
    }

    // -------------------------
    // Lesson entity
    // -------------------------
    @Test
    void createLesson() {
        Instant date = Instant.parse("2026-02-04T10:00:00Z"); // specific instant
        Lesson lesson = new Lesson();
        lesson.setLessonName("Databases");
        lesson.setDate(date);
        em.persist(lesson);
        em.flush();
        em.clear();

        Lesson found = em.find(Lesson.class, lesson.getLessonID());
        assertEquals("Databases", found.getLessonName());
        assertEquals(date, found.getDate());
    }

    // -------------------------
    // Teacher entity
    // -------------------------
    @Test
    void createTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Bob");
        teacher.setLastName("Smith");
        teacher.setEmail("bob@school.com");
        teacher.setPassword("pw");
        teacher.setAdmin(false);
        em.persist(teacher);
        em.flush();
        em.clear();

        Teacher found = em.find(Teacher.class, teacher.getTeacherID());
        assertEquals("Bob", found.getFirstName());
        assertFalse(found.isAdmin());
    }

    // -------------------------
    // Course entity
    // -------------------------
    @Test
    void createCourse() {
        Course course = new Course();
        course.setCourseName("Software Engineering");
        em.persist(course);
        em.flush();
        em.clear();

        Course found = em.find(Course.class, course.getCourseID());
        assertEquals("Software Engineering", found.getCourseName());
    }

    // -------------------------
    // Assignment entity
    // -------------------------
    @Test
    void createAssignment() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentName("Homework 1");
        assignment.setAssignmentDescription("Intro assignment");
        em.persist(assignment);
        em.flush();
        em.clear();

        Assignment found = em.find(Assignment.class, assignment.getAssignmentID());
        assertEquals("Homework 1", found.getAssignmentName());
        assertEquals("Intro assignment", found.getAssignmentDescription());
    }

    // -------------------------
    // Many-to-many: Student <-> Lesson (Attends)
    // -------------------------
    @Test
    void studentAttendsLesson() {
        // Create and persist student
        Student student = new Student();
        student.setFirstName("Alice");
        student.setLastName("Wonderland");
        student.setEmail("alice@student.com");
        student.setPassword("pw123");
        em.persist(student);

        // Create and persist lesson
        Instant date = Instant.parse("2026-02-04T10:00:00Z");
        Lesson lesson = new Lesson();
        lesson.setLessonName("Databases");
        lesson.setDate(date);
        em.persist(lesson);

        // Create join table entity
        StudentLesson attendance = new StudentLesson();
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setPresent(true);
        attendance.setReasonForAbsence(null);
        em.persist(attendance);

        em.flush();
        em.clear();

        // Fetch the join table entity
        StudentLesson found = em.find(StudentLesson.class, attendance.getId());
        assertNotNull(found);
        assertEquals("Alice", found.getStudent().getFirstName());
        assertEquals("Databases", found.getLesson().getLessonName());
        assertTrue(found.getPresent());
        assertNull(found.getReasonForAbsence());
    }
    // -------------------------
    // Many-to-many: Teacher <-> Lesson (Teaches)
    // -------------------------
    @Test
    void teacherTeachesLesson() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Bob");
        teacher.setLastName("Smith");
        teacher.setEmail("bob@school.com");
        teacher.setPassword("pw123");
        teacher.setAdmin(false);

        em.persist(teacher);

        Lesson lesson = new Lesson();
        lesson.setLessonName("JPA Basics");
        em.persist(lesson);

        teacher.getLessons().add(lesson);
        lesson.getTeachers().add(teacher);

        em.flush();
        em.clear();

        Teacher foundTeacher = em.find(Teacher.class, teacher.getTeacherID());
        assertEquals(1, foundTeacher.getLessons().size());
        assertEquals("JPA Basics", foundTeacher.getLessons().iterator().next().getLessonName());
    }
}
