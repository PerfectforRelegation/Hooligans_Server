package com.example.hooligan01.service;

import com.example.hooligan01.dto.LoginResponse;
import com.example.hooligan01.security.JwtUtil;
import com.example.hooligan01.entity.RefreshToken;
import com.example.hooligan01.repository.RefreshTokenRepository;

import com.example.hooligan01.dto.TokenDto;

import com.example.hooligan01.entity.Users;

import com.example.hooligan01.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    public List<Users> userList() {

        return userRepository.findAll();
    }

    public Users findByNickname(String nickname) {

        Optional<Users> user = userRepository.findByNickname(nickname);

        return user.get();
    }

    public Users findById(UUID id) {

        Optional<Users> user = userRepository.findById(id);

        return user.orElse(null);
    }

    public Boolean join(Users inputUser) throws Exception {

        if (userRepository.findByAccount(inputUser.getAccount()).isPresent()) {
            throw new RuntimeException("Overlap Check");
        }

        inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));

        userRepository.save(inputUser);
        return true;
    }

    public LoginResponse login(Users inputUser, HttpServletResponse response) {

        Users user = userRepository.findByAccount(inputUser.getAccount()).orElseThrow(
                () -> new RuntimeException("Not found Account"));

        if (!passwordEncoder.matches(inputUser.getPassword(), user.getPassword())) {
            throw new RuntimeException("No matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(user.getAccount());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsersAccount(user.getAccount());

        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getAccessToken(), tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getAccessToken(), tokenDto.getRefreshToken(), user);
            refreshTokenRepository.save(newToken);
            user.setToken(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .account(user.getAccount())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .betPoint(user.getBetPoint())
                .firstTeam(user.getFirstTeam())
                .secondTeam(user.getSecondTeam())
                .thirdTeam(user.getThirdTeam())
                .tokenDto(tokenDto)
                .build();
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

//    // 회원 개인 정보
//    public Users updateForm(String myLoginId) {
//
//        Optional<Users> byAccount = userRepository.findByAccount(myLoginId);
//
//        return byAccount.get();
//    }

    public Boolean update(Users user) {

        userRepository.save(user);

        return true;
    }

    // 비번 찾기
    public Users findIdPw(String account) {

        Optional<Users> byAccount = userRepository.findByAccount(account);

        return byAccount.orElse(null);
    }

    // 회원 탈퇴
    public void deleteByUserId(UUID id) {
        userRepository.deleteById(id);
    }

    // 아이디 찾기
    public Users findByNameAndBirth(String name, String birth) {

        Optional<Users> findUser = userRepository.findByNameAndBirth(name, birth);

        return findUser.orElse(null);
    }

}
