package com.api.lesson;

import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toLessonEntity(CreateLessonRequest request) {
        return new Lesson(request.getLessonName(), request.getDate());
    }

    public void updateLessonEntity(Lesson lesson, UpdateLessonRequest request) {
        if (request.getLessonName() != null) lesson.setLessonName(request.getLessonName());
        if (request.getDate() != null) lesson.setDate(request.getDate());
    }

    public LessonDto toLessonDto(Lesson lesson) {
        return new LessonDto(lesson.getLessonID(), lesson.getLessonName(), lesson.getDate());
    }
}
