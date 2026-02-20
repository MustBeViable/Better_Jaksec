package com.api.lesson;

import com.api.common.error.exceptions.BadRequestException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.lessonMapper = lessonMapper;
    }

    @Transactional
    public LessonDto create(CreateLessonRequest request) {
        //Checkers here if needed (like if unique lesson names)
        Lesson lesson = this.lessonMapper.toLessonEntity(request);
        System.out.println("LessonService.create:"+request.getCourseId());
        if (request.getCourseId() != null) {
            Course course = this.courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("Course not found"));

            lesson.setCourse(course);
        }else{
            throw new BadRequestException("Course id is missing");
        }
        System.out.println("LessonService.create:"+lesson.getCourse());
        lesson = this.lessonRepository.save(lesson);
        return this.lessonMapper.toLessonDto(lesson);
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
        Lesson lesson = lessonRepository.getReferenceById(lessonID);

        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new BadRequestException("Course not found"));
            lesson.setCourse(course);
        }

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
