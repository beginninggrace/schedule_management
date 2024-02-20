package com.sparta.schedule_management.security;

import com.sparta.schedule_management.entity.User;
import com.sparta.schedule_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl { // 따로 빼놓는 이유 : userservice에 구현해놓으면 순환참조 일어나서 - 위험성 방지

        private final UserRepository userRepository;

        public UserDetailsImpl getUserDetails(String username) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Not Found" + username));
            return new UserDetailsImpl(user);
        }

    }
