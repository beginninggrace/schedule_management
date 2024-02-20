package com.sparta.schedule_management.user.controller;

import com.sparta.schedule_management.user.dto.request.UserRequest;
import com.sparta.schedule_management.user.dto.response.UserResponse;
import com.sparta.schedule_management.user.jwt.JwtUtil;
import com.sparta.schedule_management.service.UserService;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserRequest userRequest) { // @Pattern에 적합한지도 알아야해서 @Valid 추가
        try {
            userService.signup(userRequest);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new UserResponse("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value()) // .status()말고 .created(url)넣으면 생성된 애를 상세조회할 수 있는 url을 넣는게 restful한 api의 가이드인데 나중에 참고하고 알아두기
                .body(new UserResponse("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest, HttpServletResponse response) { // HttpServletResponse로 토큰 받아오기
        try {
            userService.login(userRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new UserResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequest.getUsername())); // 만든 토큰 헤더에 담기

        return ResponseEntity.ok().body(new UserResponse("로그인 성공", HttpStatus.OK.value()));
    }
}
