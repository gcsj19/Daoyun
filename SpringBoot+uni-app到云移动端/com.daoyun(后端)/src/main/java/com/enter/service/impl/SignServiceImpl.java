package com.enter.service.impl;


import com.enter.entity.Sign;
import com.enter.entity.UserSign;
import com.enter.mapper.SignMapper;
import com.enter.mapper.UserSignMapper;
import com.enter.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignServiceImpl implements SignService {

    @Autowired
    SignMapper signMapper;
    @Autowired
    UserSignMapper userSignMapper;

    @Override
    public boolean insertSign(Sign sign) {

        if(sign == null){
            return false;
        }
        signMapper.insert(sign);
        return true;
    }

    @Override
    public boolean insertUserSign(UserSign userSign) {

        if(userSign == null)
            return false;

        userSignMapper.insert(userSign);
        return true;
    }

    @Override
    public int getSignId(String teacherId) {

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("user_id",teacherId);
        columnMap.put("alive",1);
        List<Sign> signs = signMapper.selectByMap(columnMap);
        if(signs.isEmpty())
            return -1;

        return signs.get(0).getId();
    }

    @Override
    public int getUserSignId(int signId,String userId) {

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("sign_id",signId);
        columnMap.put("user_id",userId);
        List<UserSign> userSigns = userSignMapper.selectByMap(columnMap);
        if(userSigns.isEmpty())
            return -1;
        return userSigns.get(0).getId();
    }

    @Override
    public UserSign getUserSign(int id) {

        UserSign userSign = userSignMapper.selectById(id);
        return userSign;
    }

    @Override
    public int getSignId(int courseId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("course_id",courseId);
        columnMap.put("alive",1);
        List<Sign> signs = signMapper.selectByMap(columnMap);
        if(signs.isEmpty())
            return -1;

        return signs.get(0).getId();
    }

    @Override
    public boolean updateSign(Sign sign) {


        if(sign == null)
            return false;
        signMapper.updateById(sign);
        return true;
    }

    @Override
    public Sign getSign(int id) {

        Sign sign = signMapper.selectById( id );
        return sign;
    }

    @Override
    public List<Sign> getSignList(String userId, int courseId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("course_id",courseId);
        columnMap.put("user_id",userId);
        List<Sign> signs = signMapper.selectByMap(columnMap);
        return signs;
    }

    @Override
    public List<Sign> getSignList(int courseId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("course_id",courseId);
        List<Sign> signs = signMapper.selectByMap(columnMap);
        return signs;
    }

    @Override
    public List<UserSign> getUserSignList(String userId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("user_id",userId);
        List<UserSign> userSigns = userSignMapper.selectByMap(columnMap);
        return userSigns;
    }

    @Override
    public UserSign getUserSign(String userId, int signId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("sign_id",signId);
        columnMap.put("user_id",userId);
        List<UserSign> userSigns = userSignMapper.selectByMap(columnMap);
        if(userSigns.isEmpty())
            return null;
        return userSigns.get(0);
    }


}
