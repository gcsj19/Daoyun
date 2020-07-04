package com.enter.service;

import com.enter.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getSelectedId(String name);
    int getRoleId(String name);

}
