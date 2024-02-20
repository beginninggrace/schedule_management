package com.sparta.schedule_management.service;

import com.sparta.schedule_management.user.dto.request.UserRequest;
import com.sparta.schedule_management.user.entity.User;
import com.sparta.schedule_management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void signup(UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = passwordEncoder.encode(userRequest.getPassword());

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);
    }

    public void login(UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) { // matches(입력받은 row 패스워드, 암호화된 패스워드)
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}

