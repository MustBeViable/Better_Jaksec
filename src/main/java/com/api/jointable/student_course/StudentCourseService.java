package com.api.jointable.student_course;

import com.api.common.error.exceptions.BadRequestException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import com.api.jointable.student_course.dto.CreateStudentCourse;
import com.api.jointable.student_course.dto.StudentCourseDto;
import com.api.jointable.student_course.dto.UpdateStudentCourse;
import com.api.student.Student;
import com.api.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentCourseService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseMapper mapper;

    public StudentCourseService(StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepository, StudentCourseMapper mapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.mapper = mapper;
    }

    @Transactional
    public StudentCourseDto create(CreateStudentCourse request) {

        StudentCourse studentCourse = mapper.toEntity(request);

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new BadRequestException("Invalid student id"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BadRequestException("Invalid course id"));

        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        this.studentCourseRepository.save(studentCourse);

        return mapper.toDto(studentCourse);
    }

    @Transactional(readOnly = true)
    public List<Integer> readStudentIdsByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new BadRequestException("Invalid course id");
        }

        return studentCourseRepository.findAllByCourse_CourseID(courseId)
                .stream()
                .map(sc -> sc.getStudent().getStudentID())
                .toList();
    }

    @Transactional
    public StudentCourseDto read(Long gradeId){
        StudentCourse studentCourse = this.studentCourseRepository.findById(gradeId)
                .orElseThrow(()-> new BadRequestException("Grade not found"));
        return this.mapper.toDto(studentCourse);
    }

    @Transactional
    public StudentCourseDto update(Long gradeId, UpdateStudentCourse request){
        StudentCourse studentCourse = this.studentCourseRepository.findById(gradeId)
                .orElseThrow(()-> new BadRequestException("Grade not found"));
        this.mapper.updateEntity(studentCourse,request);
        studentCourse = this.studentCourseRepository.save(studentCourse);
        return this.mapper.toDto(studentCourse);
    }
}
