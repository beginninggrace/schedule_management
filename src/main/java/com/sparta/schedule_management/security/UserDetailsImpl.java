package com.sparta.schedule_management.security;

import com.sparta.schedule_management.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// UserDetails를 상속받는 UserDetailsImpl 만들기
public class UserDetailsImpl implements UserDetails { // 여기까지 만들고 alt + enter치면 밑에 자동으로 오버라이드 생성해줌 (24열~)

    // 실제 생성자에서 user가 들어올 때
    @Getter
    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        여기에 만약 권한도 추가하고 싶으면 이거 추가
//        UserRoleEnum role = user.getRole();
//        String authority = role.getAuthority();
//
//        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(simpleGrantedAuthority);
//
//        return authorities;
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
