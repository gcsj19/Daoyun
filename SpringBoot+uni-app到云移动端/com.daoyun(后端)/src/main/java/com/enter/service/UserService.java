package com.enter.service;

import com.enter.entity.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface UserService {

    User getUserInfo(int id);
    void signUpUser(User user);
    List<User> getUserList(String phone);
    int getSelectedId(String phone);
    boolean userNameUpdate(String name,String id);




}
