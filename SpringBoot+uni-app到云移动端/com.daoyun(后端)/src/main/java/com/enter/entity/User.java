package com.enter.entity;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

@Data
public class User {

    private int id;
    private String phone;
    private String userName;
    private String email;
    private String major;
    private String isPermission;
    private Date modifyTime;
    private Date createTime;









}
