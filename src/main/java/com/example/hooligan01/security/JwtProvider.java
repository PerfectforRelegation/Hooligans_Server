package com.example.hooligan01.security;

import com.example.hooligan01.entity.Authorities;
import com.example.hooligan01.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {
// 이 클래스는 jwt 를 생성하고 검증하는 중요한 작업을 수행하며,
// Spring Security 와 함께 사용하여 사용자 인증 및 권한 부여를 지원함.
// 이를 통해 보안적으로 안전한 방식으로 사용자 인증 및 권한 부여가 수행 가능.

    @Value("${jwt.secret.key}")
    private String salt;

    // JWT 서명에 사용되는 비밀 키를 저장하는 데 사용
    private Key secretKey;

    private final UserDetailsService userDetailsService;

    // 빈이 초기화될 때 실행되고 secretKey 를 salt 를 사용하여 초기화
    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    // 사용자의 계정 및 권한 목록을 기반으로 JWT 토큰을 생성함
    public String createToken(String account, List<Authorities> roles) {

        Claims claims = Jwts.claims().setSubject(account);
        claims.put("roles", roles);
        Date now = new Date();
        // 만료시간: 1Hour
        long exp = 1000L * 60 * 60;
        return Jwts.builder() // 토큰 구성, 사용자 정보 및 만료시간 설정
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact(); // secretKey 를 사용하여 서명
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    // JWT 토큰에서 사용자 정보를 추출하고, 해당 정보를 사용하여
    // Security 의 'Authentication' 객체를 생성
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 account 획득
    // (jwt 토큰에서 사용자 정보를 추출하고)
    public String getAccount(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Authorization Header 를 통해 인증을 함
    // (HTTP 요청의 Authorization 헤더에서 jwt 토큰을 추출합니다.)
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    // (jwt 토큰의 유효성을 검사함. 토큰이 올바른 서명을 가지고 있고
    // 만료되지 않았다면 'true' 를 반환하고, 그렇지 않으면 'false' 를 반환
    public boolean validateToken(String token) {

        try {
            // Bearer 검증, "BEARER "로 시작하는지 확인함
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }

            Jws<Claims> claims = Jwts.parserBuilder()
                    // jwt 의 서명을 검증하기 위한 비밀키 설정
                    .setSigningKey(secretKey)
                    .build()
                    // 토큰을 파싱하고 서명을 검증함,
                    // 서명이 유효하고 내부 Claims 에 문제가 없다면
                    // 'claims' 객체를 얻게 됨
                    .parseClaimsJws(token);

            // 만료되었을 시 false
            // claims 객체로부터 토큰의 만료 시간을 가져옵니다.
            // getExpiration()은 토큰의 만료 시간을 나타내는
            // Date 객체를 반환합니다.
            // before(new Date())는 현재 시간과 비교하여
            // 토큰의 만료 시간이 현재 시간 이전인지 확인합니다.
            // 즉, 토큰이 아직 만료되지 않았으면 true 를 반환하고,
            // 만료되었으면 false 를 반환합니다.
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false; // 토큰 유효 X
        }
    }
}
