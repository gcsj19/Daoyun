package com.enter.service.impl;

import com.enter.entity.User;
import com.enter.mapper.UserMapper;
import com.enter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserInfo(int id){

        User user = userMapper.selectById(id);

        return user;
    }

    @Override
    public void signUpUser(User user) {

        userMapper.insert(user);

    }

    @Override
    public List<User> getUserList(String phone) {

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("phone",phone);
        List<User> users = userMapper.selectByMap(columnMap);
        return users;
    }

    @Override
    public int getSelectedId(String phone) {

        List<User> users = getUserList(phone);
        return users.get(0).getId();
    }


    @Override
    public boolean userNameUpdate(String name,String id) {

        int tableId = getSelectedId(id);
        User user = userMapper.selectById(tableId);
        if(user == null){
            return false;
        }
        user.setUserName(name);
        userMapper.updateById(user);

        return true;
    }

}
