package com.enter.entity;

import lombok.Data;

@Data
public class MemberRequestBody {
    private String userId;
    private String userName;
    private int score;


}
