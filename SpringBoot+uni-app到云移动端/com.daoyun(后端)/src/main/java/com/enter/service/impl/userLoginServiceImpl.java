package com.enter.service.impl;

import com.enter.entity.User;
import com.enter.entity.UserLogin;
import com.enter.mapper.UserLoginMapper;
import com.enter.service.userLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class userLoginServiceImpl implements userLoginService {

    @Autowired
    UserLoginMapper userLoginMapper;
    @Override
    public List<UserLogin> selected(String id){

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("user_id",id);
        List<UserLogin> list = userLoginMapper.selectByMap(columnMap);
        return list;
    }

    @Override
    public boolean insertUserLogin(UserLogin userLogin) {

        List<UserLogin> userLogins = this.selected(userLogin.getUserId());
        if(userLogins.isEmpty()){
            userLoginMapper.insert(userLogin);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public int getId(String userId,int index) {
        List<UserLogin> userLogins = this.selected(userId);
        if(userLogins.isEmpty())
            return -1;
        return userLogins.get(index).getId();
    }

    @Override
    public boolean isPermissonLogin(UserLogin userLogin) {

        int id = this.getId(userLogin.getUserId(),0);
        if (id == -1)
            return false;
        UserLogin userLogin1 = userLoginMapper.selectById(id);
        if(userLogin1 == null){
            return false;
        }
        else {
            if(userLogin1.getUserPassword().equals(userLogin.getUserPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPermissionOptions(String token) {

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("certificate",token);
        List<UserLogin> list = userLoginMapper.selectByMap(columnMap);
        if(list.isEmpty())
        {
            return false;
        }
        return true;
    }

    @Override
    public String getToken(UserLogin userLogin) {
        int id = this.getId(userLogin.getUserId(),0);
        UserLogin userLogin1 = userLoginMapper.selectById(id);
        if(isPermissonLogin(userLogin)){
                return userLogin1.getCertificate();
        }
        return null;
    }

    @Override
    public String getToken(String id) {

        int tableId = getId(id,0);
        UserLogin userLogin = userLoginMapper.selectById(tableId);
        return userLogin.getCertificate();
    }

    @Override
    public String getUserType(String id) {
        int tableId = getId(id,0);
        UserLogin userLogin = getUserLogin( tableId );

        return userLogin.getUserType();
    }

    @Override
    public UserLogin getUserLogin(int id) {

        UserLogin userLogin = userLoginMapper.selectById(id);
        return userLogin;
    }

}
