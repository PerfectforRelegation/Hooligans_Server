package com.example.hooligan01.security;

import com.example.hooligan01.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Users user;

    public CustomUserDetails(Users user) {
        this.user = user;
    }

    public final Users getUser() {
        return user;
    }

    // 사용자가 가지고 있는 권한(역할) 목록을 반환하는 메서드
    // 스프링 시큐리티에게 현재 사용자의 권한 정보를 제공하고,
    // 이 정보를 기반으로 접근 권한을 부여하거나 거부함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 해당 사용자의 권한(역할) 목록을 가져와서
        // 메서드에서 반환한 권한 목록을 스트림으로 변환하고
        // 각 권한(역할)을 SimpleGrantedAuthority 객체로 매핑하고
        // ("SimpleGrantedAuthority"는 스프링 시큐리티에서 권한을 나타내는 클래스)
        // 생성자에 권한(역할)의 이름을 전달하여 객체를 생성함
        return user.getRoles().stream().
                map(o -> new SimpleGrantedAuthority(o.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
