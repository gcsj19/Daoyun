package com.enter.entity;

import lombok.Data;

@Data
public class CourseMember {
    private int id;
    private int courseId;
    private String userId;
    private boolean isTeacher;

}
