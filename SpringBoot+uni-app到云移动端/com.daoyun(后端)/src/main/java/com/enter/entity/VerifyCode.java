package com.enter.entity;

import lombok.Data;

@Data
public class VerifyCode {

    private String code;
    private byte[] imgBytes;
    private long expireTime;

}
