package com.api.course.dto;

import java.util.HashSet;
import java.util.Set;

public class CourseDto {

    private Long id;
    private String name;
    private String language;
    private Set<Long> lessonIds = new HashSet<>();
    private Set<Long> assignmentIds = new HashSet<>();
    private Set<String> teacherNames = new HashSet<>();

    public CourseDto() {}

    public CourseDto(Long id,String name, String language, Set<Long> lessonIds, Set<Long> assignmentIds, Set<String> teacherNames) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.lessonIds = lessonIds;
        this.assignmentIds = assignmentIds;
        this.teacherNames = teacherNames;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getLanguage() { return this.language; }
    public Set<Long> getLessonIds() { return this.lessonIds; }
    public Set<Long> getAssignmentIds() { return this.assignmentIds; }
    public Set<String> getTeacherNames() { return this.teacherNames; }
}