package com.api.jointable.student_lesson;

import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.lesson.LessonRepository;
import com.api.login.Auth;
import com.api.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(this.studentRepository.findById(request.getStudentId()).isPresent())
            attendance.setStudent(this.studentRepository.findById(request.getStudentId()).get());
        if(this.lessonRepository.findById(request.getLessonId()).isPresent())
            attendance.setLesson(this.lessonRepository.findById(request.getLessonId()).get());
        if(auth.getRole().equalsIgnoreCase("student")
                && !attendance.getStudent().getEmail().equalsIgnoreCase(auth.getEmail())){
            throw new UnauthorizedException("Students can only mark their own attendance");
        }
        this.studentLessonRepository.save(attendance);
        return this.mapper.toDto(attendance);
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
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getRole().equalsIgnoreCase("teacher")){
            throw new UnauthorizedException("Only admins and teachers can update attendance");
        }
        StudentLesson attendance = this.studentLessonRepository.findById(attendanceId)
                .orElseThrow(() -> new BadRequestException("Attendance record doesnt exist"));
        this.mapper.updateEntity(attendance,request);
        attendance = this.studentLessonRepository.save(attendance);
        return this.mapper.toDto(attendance);
    }

    @Transactional
    public void delete(Long attendanceId, Auth auth){
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getRole().equalsIgnoreCase("teacher")){
            throw new UnauthorizedException("Only admins and teachers can delete attendance");
        }
        this.studentLessonRepository.deleteById(attendanceId);
    }

}
