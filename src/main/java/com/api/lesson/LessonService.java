package com.api.lesson;

import com.api.common.Language;
import com.api.common.LanguageRepository;
import com.api.common.error.exceptions.BadRequestException;
import com.api.common.error.exceptions.NotFoundException;
import com.api.common.error.exceptions.UnauthorizedException;
import com.api.course.Course;
import com.api.course.CourseRepository;
import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import com.api.login.Auth;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LanguageRepository languageRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, LanguageRepository languageRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.languageRepository = languageRepository;
        this.lessonMapper = lessonMapper;
    }

    @Transactional
    public LessonDto create(CreateLessonRequest request, Auth auth) {
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getRole().equalsIgnoreCase("teacher")){
            throw new UnauthorizedException("Only admins and teachers can create lessons");
        }

        Language language = this.languageRepository.findById(request.getLocale())
                .orElseThrow(()-> new NotFoundException("Unknown language"));

        //Checkers here if needed (like if unique lesson names)
        Lesson lesson = this.lessonMapper.toLessonEntity(request);
        lesson.setLanguage(language);
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
    public LessonDto read(Long lessonID, Auth auth) {
        if (!lessonRepository.existsById(lessonID)) {
            throw new BadRequestException("Lesson doesn't exists.");
        }
        if(auth.getRole().equalsIgnoreCase("admin")
                ||auth.getRole().equalsIgnoreCase("teacher")){
            Lesson lesson = lessonRepository.getReferenceById(lessonID);
            return lessonMapper.toLessonDto(lesson);
        }else if(this.lessonRepository.isStudentEnrolledInLesson(auth.getEmail(),lessonID)){
            return this.lessonMapper.toLessonDto(lessonRepository.getReferenceById(lessonID));
        }else {
            throw new UnauthorizedException("IDK what you broke, this isnt supposed to be thrown");
        }
    }

    @Transactional
    public LessonDto update(Long lessonID, UpdateLessonRequest request, Auth auth) {
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getRole().equalsIgnoreCase("teacher")){
            throw new UnauthorizedException("Only admin and teacher can update lesson");
        }

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
    public void delete(Long lessonID, Auth auth) {
        if(!auth.getRole().equalsIgnoreCase("admin")
                && !auth.getRole().equalsIgnoreCase("teacher")){
            throw new UnauthorizedException("Only admin and teacher can delete lesson");
        }
        Lesson lesson = lessonRepository.findById(lessonID)
                .orElseThrow( () -> new BadRequestException("Lesson doesn't exists."));

        lessonRepository.delete(lesson);
    }
}
