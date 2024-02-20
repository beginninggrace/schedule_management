package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//jpa repository 상속받으면 @Repository 안 넣어도 됨
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
