package com.example.study0126.controllers.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String password;
    private String email;
}
