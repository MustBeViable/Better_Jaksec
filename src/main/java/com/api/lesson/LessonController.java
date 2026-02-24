package com.api.lesson;

import com.api.common.util.JwtUtils;
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
    public LessonDto postLesson(@RequestHeader("Authorization") String token,
                                @Valid @RequestBody CreateLessonRequest request) {
        return lessonService.create(request, JwtUtils.toAuth(token));
    }

    @GetMapping("/{lessonID}")
    public LessonDto getLesson(@RequestHeader("Authorization") String token,
                               @PathVariable Long lessonID) {
        return lessonService.read(lessonID,JwtUtils.toAuth(token));
    }

    @PutMapping("/{lessonID}")
    public LessonDto putLesson(@RequestHeader("Authorization") String token,
            @PathVariable Long lessonID,
            @Valid @RequestBody UpdateLessonRequest request) {
        return lessonService.update(lessonID, request,JwtUtils.toAuth(token));
    }

    @DeleteMapping("/{lessonID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLesson(@RequestHeader("Authorization") String token,
            @PathVariable Long lessonID) {
        lessonService.delete(lessonID,JwtUtils.toAuth(token));
    }

}
