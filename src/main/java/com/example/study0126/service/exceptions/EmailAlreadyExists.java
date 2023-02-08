package com.example.study0126.service.exceptions;

public class EmailAlreadyExists extends CommonException {
    public EmailAlreadyExists(String code, String message) {
        super(code, message);
    }
}
