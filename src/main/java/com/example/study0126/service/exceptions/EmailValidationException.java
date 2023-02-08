package com.example.study0126.service.exceptions;

public class EmailValidationException extends CommonException {
    public EmailValidationException(String code, String message) {
        super(code, message);
    }
}