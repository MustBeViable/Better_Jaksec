package com.api.lesson;

import com.api.lesson.dto.CreateLessonRequest;
import com.api.lesson.dto.LessonDto;
import com.api.lesson.dto.UpdateLessonRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public LessonDto postLesson(@Valid @RequestBody CreateLessonRequest request) {
        return lessonService.create(request);
    }

    @GetMapping("/{lessonID}")
    public LessonDto getLesson(@PathVariable Long lessonID) {
        return lessonService.read(lessonID);
    }

    @PutMapping("/{lessonID}")
    public LessonDto putLesson(
            @PathVariable Long lessonID,
            @Valid @RequestBody UpdateLessonRequest request
    ) {
        return lessonService.update(lessonID, request);
    }

    @DeleteMapping("/{lessonID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@PathVariable Long lessonID) {
        lessonService.delete(lessonID);
    }

}
