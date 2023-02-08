package com.example.study0126.controllers;

import com.example.study0126.controllers.dtos.CreateUserRequest;
import com.example.study0126.controllers.dtos.ErrorResponse;
import com.example.study0126.controllers.dtos.UpdateUserRequest;
import com.example.study0126.controllers.dtos.UserResponse;
import com.example.study0126.service.UserService;
import com.example.study0126.service.exceptions.EmailAlreadyExists;
import com.example.study0126.service.exceptions.PasswordValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import com.example.study0126.service.exceptions.CommonException;
import com.example.study0126.service.exceptions.EmailValidationException;
import com.example.study0126.service.exceptions.NameValidationException;
import com.example.study0126.service.exceptions.UserNotExist;
@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    // create new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> create(@RequestBody Mono<CreateUserRequest> createUserRequest) {
        return createUserRequest
                .log()
                .filter(CreateUserRequest::isPasswordMatched)
                .switchIfEmpty(Mono.error(new PasswordValidationException("10004", "password not matched")))
                .map(EntityDtoUtil::toEntity)
                .map(userService::create)
                .map(EntityDtoUtil::toUserDto);
    }

    // update user
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> update(@PathVariable Long id, @RequestBody Mono<UpdateUserRequest> updateUserRequest) {
        return updateUserRequest
                .log()
                .map(EntityDtoUtil::toEntity)
                .map(user -> userService.update(id, user))
                .map(EntityDtoUtil::toUserDto);
    }

    // delete by id
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    // get user by id
    @GetMapping("{id}")
    public Mono<UserResponse> get(@PathVariable Long id) {
        return Mono.just(userService.getById(id))
                .map(EntityDtoUtil::toUserDto);
    }

    // get all users
    @GetMapping
    public Flux<UserResponse> getAll() {
        return Flux
                .fromIterable(userService.getAll())
                .map(EntityDtoUtil::toUserDto);
    }

    // exception handling
    @ExceptionHandler(EmailAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ErrorResponse> handleEmailAlreadyExists(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }

    @ExceptionHandler({EmailValidationException.class, PasswordValidationException.class, NameValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleEmailLengthException(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }

    @ExceptionHandler(UserNotExist.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleUserNotFoundException(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }
}