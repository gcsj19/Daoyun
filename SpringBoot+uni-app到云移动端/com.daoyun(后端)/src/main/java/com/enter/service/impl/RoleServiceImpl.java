package com.enter.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.enter.entity.Role;
import com.enter.mapper.RoleMapper;
import com.enter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<Role> getSelectedId(String name){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name",name);
        List<Role> roles = roleMapper.selectByMap(columnMap);
        return roles;

    }

    @Override
    public int getRoleId(String name) {
        List<Role> roles = getSelectedId(name);
        return roles.get(0).getId();
    }

}
