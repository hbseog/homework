package com.example.study0126.controllers;

import com.example.study0126.controllers.dtos.ErrorResponse;
import com.example.study0126.service.exceptions.CommonException;
import com.example.study0126.service.exceptions.PasswordAndEmailException;
import com.example.study0126.service.exceptions.UserNotExist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.example.study0126.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RestController
@RequestMapping("check-password")
public class CheckPasswordController {
    private final UserService userService;

    @PostMapping("{email}")
    public boolean checkPassword(@PathVariable String email, @RequestBody String password) {
        if (!userService.checkPassword(email, password)) {
            throw new PasswordAndEmailException("10004", "email and password not matched");
        }
        return userService.checkPassword(email, password);

        // 숙제 : 패스워드 실패시 401 Unauthorized 응답을 내려주도록 수정해보세요.
        // EventHandlers 에서 401 응답을 내려주는 방법을 참고하세요.
    }
    @ExceptionHandler(PasswordAndEmailException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ErrorResponse> handlePasswordAndEmailException(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }
}