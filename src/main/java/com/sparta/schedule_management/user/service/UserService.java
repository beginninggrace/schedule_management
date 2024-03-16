package com.sparta.schedule_management.user.service;

import com.sparta.schedule_management.exception.NotExistsUserException;
import com.sparta.schedule_management.exception.NotMatchedPasswordException;
import com.sparta.schedule_management.user.dto.request.UserRequest;
import com.sparta.schedule_management.user.entity.User;
import com.sparta.schedule_management.user.jwt.JwtUtil;
import com.sparta.schedule_management.user.repository.UserRepository;
import com.sparta.schedule_management.user.util.PasswordEncoderUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoderUtil passwordEncoderUtil;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void signup(UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = passwordEncoderUtil.passwordEncoder().encode(userRequest.getPassword());

        if (userRepository.existsByUsername(username)) {
            throw new NotExistsUserException();
        }

        User user = new User(username, password);
        userRepository.save(user);
    }

    public void login(UserRequest userRequest, HttpServletResponse response) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();

        User user = userRepository.findByUsername(username)
            .orElseThrow(NotExistsUserException::new);

        if (!passwordEncoderUtil.passwordEncoder()
            .matches(password, user.getPassword())) {
            throw new NotMatchedPasswordException();
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER,
            jwtUtil.createToken(userRequest.getUsername()));
    }
}

