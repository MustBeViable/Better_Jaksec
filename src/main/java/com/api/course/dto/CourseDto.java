package com.api.course.dto;

import java.util.Set;

public class CourseDto {

    private Long id;
    private String name;
    private Set<Long> lessonIds;
    private Set<Long> assignmentIds;
    private Set<Integer> teacherIds;

    public CourseDto() {}

    public CourseDto(Long id,String name, Set<Long> lessonIds, Set<Long> assignmentIds, Set<Integer> teacherIds) {
        this.id = id;
        this.name = name;
        this.lessonIds = lessonIds;
        this.assignmentIds = assignmentIds;
        this.teacherIds = teacherIds;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public Set<Long> getLessonIds() { return this.lessonIds; }
    public Set<Long> getAssignmentIds() { return this.assignmentIds; }
    public Set<Integer> getTeacherIds() { return this.teacherIds; }
}