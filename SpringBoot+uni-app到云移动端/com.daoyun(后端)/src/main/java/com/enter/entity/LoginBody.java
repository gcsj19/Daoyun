package com.enter.entity;

import lombok.Data;

@Data
public class LoginBody {
    private String id;
    private boolean isPermission;
    private String token;

}
