package com.api.jointable.student_lesson;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.lesson.Lesson;
import com.api.lesson.LessonRepository;
import com.api.login.Auth;
import com.api.student.Student;
import com.api.student.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentLessonService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final StudentLessonRepository studentLessonRepository;
    private final StudentLessonMapper mapper;

    public StudentLessonService(StudentRepository studentRepository, LessonRepository lessonRepository, StudentLessonRepository studentLessonRepository, StudentLessonMapper mapper) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.studentLessonRepository = studentLessonRepository;
        this.mapper = mapper;
    }

    @Transactional
    public StudentLessonDto create(CreateStudentLesson request, Auth auth){
        StudentLesson attendance = this.mapper.toEntity(request);

        Student student = this.studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new BadRequestException("Student doesn't exist."));

        Lesson lesson = this.lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new BadRequestException("Lesson doesn't exist."));

        attendance.setStudent(student);
        attendance.setLesson(lesson);

        if(auth.getRole().equalsIgnoreCase("student")
                && !attendance.getStudent().getEmail().equalsIgnoreCase(auth.getEmail())){
            throw new UnauthorizedException("Students can only mark their own attendance");
        }

        if(!studentLessonRepository.existsByStudentIdAndLessonId(
                request.getStudentId(),
                request.getLessonId())){
            this.studentLessonRepository.save(attendance);
        }

        return this.mapper.toDto(attendance);
    }

    @Transactional
    public Set<StudentLessonDto> findAllByLessonId(Long lessonId, Auth auth){
        if(auth.getRole().equalsIgnoreCase("admin")){
            return this.studentLessonRepository.findAll()
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
        } else if(auth.getRole().equalsIgnoreCase("teacher")){
            return this.studentLessonRepository.getStudentLessonByLessonId(lessonId)
                    .stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toSet());
        }
        else{
            throw new UnauthorizedException("You are a student");
        }
    }

    @Transactional
    public StudentLessonDto read(Long attendanceId, Auth auth){
        StudentLesson attendance = this.studentLessonRepository.findById(attendanceId)
                .orElseThrow(() -> new BadRequestException("Attendance record doesnt exist"));
        if(auth.getRole().equalsIgnoreCase("student")
                && !attendance.getStudent().getEmail().equalsIgnoreCase(auth.getEmail())){
            throw new UnauthorizedException("Students can only read their own attendance");
        }
        return this.mapper.toDto(attendance);
    }

    @Transactional
    public StudentLessonDto update(Long attendanceId, UpdateStudentLesson request, Auth auth){
        StudentLesson attendance = this.studentLessonRepository.findById(attendanceId)
                .orElseThrow(() -> new BadRequestException("Attendance record doesnt exist"));
        if(auth.getRole().equalsIgnoreCase("admin")
                || auth.getRole().equalsIgnoreCase("teacher")){
            this.mapper.updateEntity(attendance,request);
            attendance = this.studentLessonRepository.save(attendance);
            return this.mapper.toDto(attendance);
        }else if(attendance.getStudent().getEmail().equalsIgnoreCase(auth.getEmail())){
            attendance.setReasonForAbsence(request.getReason());
            attendance = this.studentLessonRepository.save(attendance);
            return this.mapper.toDto(attendance);
        }else{
            throw new UnauthorizedException("Only admins and teachers can update attendance");
        }
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long attendanceId, Auth auth){
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getRole().equalsIgnoreCase("teacher")){
            throw new UnauthorizedException("Only admins and teachers can delete attendance");
        }
        this.studentLessonRepository.deleteById(attendanceId);
    }

}
