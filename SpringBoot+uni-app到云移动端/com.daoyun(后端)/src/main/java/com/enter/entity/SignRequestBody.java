package com.enter.entity;

import lombok.Data;

@Data
public class SignRequestBody {
    private boolean alive;
    private int courseId;
    private String endTime;

}
