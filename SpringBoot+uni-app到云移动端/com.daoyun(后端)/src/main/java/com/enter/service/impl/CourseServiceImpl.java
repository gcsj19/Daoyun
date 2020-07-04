package com.enter.service.impl;

import com.enter.entity.Course;
import com.enter.entity.CourseMember;
import com.enter.mapper.CourseMapper;
import com.enter.mapper.CourseMemberMapper;
import com.enter.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseMemberMapper courseMemberMapper;

    @Override
    public Course getCourse(int id) {

        Course course = courseMapper.selectById(id);
        return course;
    }

    @Override
    public int getId(String userId,String name) {

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name",name);
        columnMap.put("user_id",userId);

        List<Course> courses = courseMapper.selectByMap(columnMap);
        if(courses.isEmpty())
            return -1;
        return courses.get(0).getId();
    }

    @Override
    public List<Course> getCourse(String userId) {

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("user_id",userId);
        List<Course> courses = courseMapper.selectByMap(columnMap);
        return courses;
    }

    @Override
    public List<CourseMember> getMember(int courseId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("course_id",courseId);
        columnMap.put("is_teacher",0);
        List<CourseMember> courseMembers = courseMemberMapper.selectByMap(columnMap);

        return courseMembers;
    }

    @Override
    public List<CourseMember> getMember(String userId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("user_id",userId);
        List<CourseMember> courseMembers = courseMemberMapper.selectByMap(columnMap);
        return courseMembers;
    }
}
