package com.enter.service;

import com.enter.entity.Sign;
import com.enter.entity.UserSign;

import java.util.List;

public interface SignService {

    boolean insertSign(Sign sign);
    boolean insertUserSign(UserSign userSign);
    int getSignId(String teacherId);
    int getUserSignId(int signId,String user_id);
    UserSign getUserSign(int id);


    int getSignId(int courseId);
    boolean updateSign(Sign sign);
    Sign getSign(int id);
    List<Sign> getSignList(String userId,int courseId);
    List<Sign> getSignList(int courseId);
    List<UserSign> getUserSignList(String userId);
    UserSign getUserSign(String userId,int signId);



}
