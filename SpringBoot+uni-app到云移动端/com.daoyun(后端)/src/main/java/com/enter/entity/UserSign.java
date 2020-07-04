package com.enter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserSign {

    private int id;
    private int signId;
    private String userId;
    private Date createTime;
    private double longitude;
    private double latitude;

}
