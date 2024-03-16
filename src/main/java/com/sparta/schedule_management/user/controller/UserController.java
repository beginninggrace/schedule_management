package com.sparta.schedule_management.user.controller;

import com.sparta.schedule_management.user.dto.request.UserRequest;
import com.sparta.schedule_management.user.dto.response.UserResponse;
import com.sparta.schedule_management.user.jwt.JwtUtil;
import com.sparta.schedule_management.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(
        @Valid @RequestBody UserRequest userRequest) {
        userService.signup(userRequest);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
        @RequestBody UserRequest userRequest,
        HttpServletResponse response) {
        userService.login(userRequest, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
