package com.enter.service;

import com.enter.entity.User;
import com.enter.entity.UserLogin;
import com.enter.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface userLoginService {

    List<UserLogin> selected(String id);
    int getId(String userId,int index);
    boolean insertUserLogin(UserLogin userLogin);
    boolean isPermissonLogin(UserLogin userLogin);
    boolean isPermissionOptions(String token);
    String getToken(UserLogin userLogin);
    String getToken(String id);
    String getUserType(String id);
    UserLogin getUserLogin(int id);





}
