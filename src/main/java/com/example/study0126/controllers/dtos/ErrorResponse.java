package com.example.study0126.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.PrimitiveIterator;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
