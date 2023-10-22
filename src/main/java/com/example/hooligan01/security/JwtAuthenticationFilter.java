package com.example.hooligan01.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Jwt 토큰을 검증하고, 유효한 토큰을 가진 사용자에게
// 권한을 부여하는 역할을 하는 Filter

// Filter 를 적용함으로써 servlet 에 도달하기 전에 검증을 완료할 수 있음.
// Filter 동작의 자세한 과정은 아래 Security Config 설정
// OncePerRequestFilter 는 단 한번의 요청에
// 단 한번만 동작하도록 보장된 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // jwt 토큰의 유효성을 검사하고 사용자 정보를 추출할 수 있음
    private final JwtProvider jwtProvider;

    // HTTP 요청을 필터링하고 처리하는 역할을 함
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 토큰 추출. "Authorization"헤더에서 토큰을 가져옴
        String token = jwtProvider.resolveToken(request);

        // 토큰이 존재하고 유효한 경우에만 처리를 수행
        if (token != null && jwtProvider.validateToken(token)) {
            // check access token("BEARER" 부분을 제거하여 토큰 부분만 남김)
            token = token.split(" ")[1].trim();

            // 토큰에서 사용자 정보를 추출하고,
            // 이를 기반으로 "Authentication"객체를 생성함
            Authentication auth = jwtProvider.getAuthentication(token);

            // "Authentication"객체를 설정.
            // 이렇게 하면 Spring Security 가 사용자를 인증하고
            // 권한을 부여할 수 있음
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 이 코드는 다음 필터 또는 서블릿으로 요청을 계속 전달함.
        // 필터 체인에서 현재 필터 이후의 처리를 계속할 수 있도록 함.
        filterChain.doFilter(request, response);
    }
}
