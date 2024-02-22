package com.sparta.schedule_management.user.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil { // 패턴매칭할 때 matches

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
