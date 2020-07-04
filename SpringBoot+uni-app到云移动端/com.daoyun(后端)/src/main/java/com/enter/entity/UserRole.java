package com.enter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserRole {
    private int id;
    private String userId;
    private int roleId;
    private Date modifyTime;
    private Date createTime;



}
