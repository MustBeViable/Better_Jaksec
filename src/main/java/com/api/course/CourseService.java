package com.api.course;

import com.api.assignment.Assignment;
import com.api.assignment.AssignmentRespository;
import com.api.common.error.BadRequestException;
import com.api.course.dto.CourseDto;
import com.api.course.dto.CreateCourseRequest;
import com.api.course.dto.UpdateCourseRequest;
import com.api.lesson.Lesson;
import com.api.lesson.LessonRepository;
import com.api.teacher.Teacher;
import com.api.teacher.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final AssignmentRespository assignmentRespository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository courseRespository, LessonRepository lessonRepository, TeacherRepository teacherRepository, AssignmentRespository assignmentRespository, CourseMapper mapper) {
        this.courseRepository = courseRespository;
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.assignmentRespository = assignmentRespository;
        this.mapper = mapper;
    }
    @Transactional
    public CourseDto create(CreateCourseRequest request){
        Course course = this.mapper.toEmptyCourseEntity(request);

        course.setAssignments(new HashSet<>(this.assignmentRespository.findAllById(request.getAssignmentIds())));
        course.setLessons(new HashSet<>(this.lessonRepository.findAllById(request.getLessonIds())));
        course.setTeachers(new HashSet<>(this.teacherRepository.findAllById(request.getTeacherIds())));

        this.courseRepository.save(course);
        return this.mapper.toCourseDto(course);
    }

    @Transactional
    public CourseDto read(Long courseId){
        Course course =  this.courseRepository.findById(courseId)
                .orElseThrow( () -> new BadRequestException("Course doesn't exist."));

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
            Set<Assignment> assignments = new HashSet<>(this.assignmentRespository.findAllById(request.getAssignmentIds()));

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

        return this.mapper.toCourseDto(course);
    }

    @Transactional
    public void delete(Long courseId){
        this.courseRepository.findById(courseId)
                .orElseThrow( () -> new BadRequestException("Course doesn't exist."));
        this.courseRepository.deleteById(courseId);
    }
}
