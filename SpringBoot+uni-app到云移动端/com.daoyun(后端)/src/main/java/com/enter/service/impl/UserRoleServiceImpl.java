package com.enter.service.impl;

import com.enter.entity.UserRole;
import com.enter.mapper.UserRoleMapper;
import com.enter.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    public void insert(UserRole userRole){

        userRoleMapper.insert(userRole);


    }
}
