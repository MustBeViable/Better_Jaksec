package com.api.course;

import com.api.assignment.Assignment;
import com.api.assignment.AssignmentRepository;
import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.NotFoundException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.jointable.student_course.StudentCourse;
import com.api.lesson.Lesson;
import com.api.lesson.LessonRepository;
import com.api.login.Auth;
import com.api.student.Student;
import com.api.teacher.Teacher;
import com.api.teacher.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final AssignmentRepository assignmentRepository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository, TeacherRepository teacherRepository, AssignmentRepository assignmentRespository, CourseMapper mapper) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.assignmentRepository = assignmentRespository;
        this.mapper = mapper;
    }

    @Transactional
    public CourseDto create(CreateCourseRequest request, Auth auth) {
        System.out.println("CourseService.create.auth: " + auth);
        Course course = mapper.toEmptyCourseEntity(request);

        Set<Lesson> lessons = new HashSet<>(lessonRepository.findAllById(request.getLessonIds()));
        for (Lesson lesson : lessons) {
            lesson.setCourse(course);
        }
        course.setLessons(lessons);

        Set<Assignment> assignments = new HashSet<>(assignmentRepository.findAllById(request.getAssignmentIds()));
        for (Assignment assignment : assignments) {
            assignment.setCourse(course);
        }
        course.setAssignments(assignments);

        Set<Teacher> teachers = new HashSet<>(teacherRepository.findAllById(request.getTeacherIds()));
        course.setTeachers(teachers);
        for (Teacher teacher : teachers) {
            teacher.getCourses().add(course);
        }

        course = courseRepository.save(course);

        if(!auth.getRole().equalsIgnoreCase("admin")){
            throw new UnauthorizedException("Only admins can create courses");
        }

        return mapper.toCourseDto(course);
    }

    @Transactional
    public List<CourseDto> readAll() {
        return courseRepository
                .findAll()
                .stream()
                .map(mapper::toCourseDto)
                .toList();
    }

    @Transactional
    public CourseDto read(Long courseId, Auth auth){
        System.out.println("CourseService.read.auth: " + auth);
        Course course =  this.courseRepository.findById(courseId)
                .orElseThrow( () -> new BadRequestException("Course doesn't exist."));
        course.getGrades().stream()
                .map(StudentCourse::getStudent)
                .map(Student::getEmail)
                .toList().contains(auth.getEmail());
        if(!auth.getRole().equalsIgnoreCase("admin") &&
            !course.getTeachers().stream()
                    .map(Teacher::getEmail)
                    .toList().contains(auth.getEmail()) &&
            !course.getGrades().stream()
                    .map(StudentCourse::getStudent)
                    .map(Student::getEmail)
                    .toList().contains(auth.getEmail())){
            throw new UnauthorizedException("Only admins or teachers and students who belong to the course can see it");
        }
        return this.mapper.toCourseDto(course);
    }

    public CourseDto update(Long courseId, UpdateCourseRequest request) {

        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException("Course not found"));

        this.mapper.updateCourseEntity(course, request);

        if (request.getLessonIds() != null) {
            Set<Lesson> lessons = new HashSet<>(this.lessonRepository.findAllById(request.getLessonIds()));

            for (Lesson lesson : course.getLessons()) {
                lesson.setCourse(null);
            }

            for (Lesson lesson : lessons) {
                lesson.setCourse(course);
            }

            course.setLessons(lessons);
        }

        if (request.getAssignmentIds() != null) {
            Set<Assignment> assignments = new HashSet<>(this.assignmentRepository.findAllById(request.getAssignmentIds()));

            for (Assignment assignment : course.getAssignments()) {
                assignment.setCourse(null);
            }

            for (Assignment assignment : assignments) {
                assignment.setCourse(course);
            }

            course.setAssignments(assignments);
        }

        if (request.getTeacherIds() != null) {
            Set<Teacher> teachers = new HashSet<>(this.teacherRepository.findAllById(request.getTeacherIds()));

            for (Teacher teacher : course.getTeachers()) {
                teacher.getCourses().remove(course);
            }

            for (Teacher teacher : teachers) {
                teacher.getCourses().add(course);
            }

            course.setTeachers(teachers);
        }
        this.courseRepository.save(course);
        return this.mapper.toCourseDto(course);
    }

    @Transactional
    public void delete(Long courseId){
        this.courseRepository.findById(courseId)
                .orElseThrow( () -> new BadRequestException("Course doesn't exist."));
        this.courseRepository.deleteById(courseId);
    }
}
