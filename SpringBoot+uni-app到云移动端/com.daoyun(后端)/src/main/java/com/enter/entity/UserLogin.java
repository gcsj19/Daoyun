package com.enter.entity;

import lombok.Data;

@Data
public class UserLogin {

    private int id;
    private String userId;
    private String userPassword;
    private String userType;
    private String certificate;


}
