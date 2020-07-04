package com.enter.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Sign {
    private int id;
    private int courseId;
    private String userId;
    private Timestamp createTime;
    private Timestamp endTime;
    private double longitude;
    private double latitude;
    private boolean alive;
}
