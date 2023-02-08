package com.example.study0126.service.exceptions;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private final String code;
    private final String message;
    public CommonException(String code, String message){
        super(String.format("%s: %s", code,message));
        this.code = code;
        this.message = message;
    }
}
