package com.example.study0126.service.exceptions;

public class PasswordValidationException extends CommonException {
    public PasswordValidationException(String code, String message) {
        super(code, message);
    }
}

