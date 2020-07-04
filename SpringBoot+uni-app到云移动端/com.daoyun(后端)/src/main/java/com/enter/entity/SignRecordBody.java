package com.enter.entity;

import lombok.Data;

@Data
public class SignRecordBody {
    private int signId;
    private int courseId;
    private String courseName;
    private String createTime;
    private String longitude;
    private String latitude;
    private String status;
    private boolean alive;

}
