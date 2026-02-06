package com.api.lesson;

import com.api.common.error.BadRequestException;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import com.api.student.Student;
import com.api.student.dto.StudentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    @Transactional
    public LessonDto create(CreateLessonRequest request) {
        //Checkers here if needed (like if unique lesson names)

        Lesson saved = lessonRepository.save(lessonMapper.toLessonEntity(request));
        return lessonMapper.toLessonDto(saved);
    }

    @Transactional
    public LessonDto read(Long lessonID) {
        if (!lessonRepository.existsById(lessonID)) {
            throw new BadRequestException("Lesson doesn't exists.");
        }

        Lesson lesson = lessonRepository.getReferenceById(lessonID);
        return lessonMapper.toLessonDto(lesson);
    }

    @Transactional
    public LessonDto update(Long lessonID, UpdateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonID)
                .orElseThrow( () -> new BadRequestException("Lesson doesn't exists."));

        lessonMapper.updateLessonEntity(lesson, request);
        lessonRepository.save(lesson);

        return lessonMapper.toLessonDto(lesson);
    }

    @Transactional
    public void delete(Long lessonID) {
        Lesson lesson = lessonRepository.findById(lessonID)
                .orElseThrow( () -> new BadRequestException("Lesson doesn't exists."));

        lessonRepository.delete(lesson);
    }
}
