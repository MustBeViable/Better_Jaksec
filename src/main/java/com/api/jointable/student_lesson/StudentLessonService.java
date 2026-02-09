package com.api.jointable.student_lesson;

import com.api.common.error.BadRequestException;
import com.api.jointable.student_lesson.dto.CreateStudentLesson;
import com.api.jointable.student_lesson.dto.StudentLessonDto;
import com.api.jointable.student_lesson.dto.UpdateStudentLesson;
import com.api.lesson.LessonRepository;
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
    public StudentLessonDto create(CreateStudentLesson request){
        StudentLesson entity = this.mapper.toEntity(request);
        if(this.studentRepository.findById(request.getStudentId()).isPresent())
            entity.setStudent(this.studentRepository.findById(request.getStudentId()).get());
        if(this.lessonRepository.findById(request.getLessonId()).isPresent())
            entity.setLesson(this.lessonRepository.findById(request.getLessonId()).get());
        this.studentLessonRepository.save(entity);
        return this.mapper.toDto(entity);
    }

    @Transactional
    public StudentLessonDto read(Long attendanceId){
        StudentLesson entity = this.studentLessonRepository.findById(attendanceId)
                .orElseThrow(() -> new BadRequestException("Attendance record doesnt exist"));
        return this.mapper.toDto(entity);
    }

    @Transactional
    public StudentLessonDto update(Long attendanceId, UpdateStudentLesson request){
        StudentLesson entity = this.studentLessonRepository.findById(attendanceId)
                .orElseThrow(() -> new BadRequestException("Attendance record doesnt exist"));
        this.mapper.updateEntity(entity,request);
        entity = this.studentLessonRepository.save(entity);
        return this.mapper.toDto(entity);
    }


}
