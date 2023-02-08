package com.example.study0126.service.exceptions;

public class UserNotExist extends CommonException {
    public UserNotExist(String code, String message) {
        super(code, message);
    }
}