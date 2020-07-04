package com.enter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Role {
    private int id;
    private String name;
    private Date modifyTime;
    private Date createTime;

}
