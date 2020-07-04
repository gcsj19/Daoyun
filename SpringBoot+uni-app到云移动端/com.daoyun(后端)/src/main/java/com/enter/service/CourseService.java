package com.enter.service;

import com.enter.entity.Course;
import com.enter.entity.CourseMember;

import java.util.List;

public interface CourseService {

    Course getCourse(int id);
    int getId(String userId,String name);
    List<Course> getCourse(String userId);
    List<CourseMember> getMember(int courseId);
    List<CourseMember> getMember(String userId);


}
