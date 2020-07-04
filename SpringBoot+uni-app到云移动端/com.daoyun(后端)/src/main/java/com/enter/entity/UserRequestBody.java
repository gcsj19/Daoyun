package com.enter.entity;

import lombok.Data;

@Data
public class UserRequestBody {
    private String phone;
    private String userName;
    private String email;
    private String major;
    private String userType;

}
