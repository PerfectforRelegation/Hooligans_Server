package com.example.hooligan01.service;

import com.example.hooligan01.dto.LoginResponse;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.dto.UserBetPointDTO;
import com.example.hooligan01.entity.Bets;
import com.example.hooligan01.repository.BetRepository;
import com.example.hooligan01.security.JwtUtil;
import com.example.hooligan01.entity.RefreshToken;
import com.example.hooligan01.repository.RefreshTokenRepository;

import com.example.hooligan01.dto.TokenDTO;

import com.example.hooligan01.entity.Users;

import com.example.hooligan01.repository.UserRepository;

import com.example.hooligan01.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BetRepository betRepository;

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

    public Message join(Users inputUser) {

        if (userRepository.findByAccount(inputUser.getAccount()).isPresent()) {
            return Message.builder()
                    .message("아이디 중복")
                    .build();
        }

        inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));

        if (inputUser.getBetPoint() == 0)
            inputUser.setBetPoint(5000);

        userRepository.save(inputUser);
        return Message.builder()
                .message("회원가입 성공")
                .build();
    }

    public ResponseEntity<Object> login(Users inputUser, HttpServletResponse response) {

        Users user;

        Optional<Users> userCheck = userRepository.findByAccount(inputUser.getAccount());

        if (userCheck.isEmpty()) {
            Message message = new Message("아이디 없음");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else
            user = userCheck.get();

        if (!passwordEncoder.matches(inputUser.getPassword(), user.getPassword())) {
            Message message = new Message("비밀번호 불일치");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        TokenDTO tokenDto = jwtUtil.createAllToken(user.getAccount());

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

        LoginResponse loginResponse =  LoginResponse.builder()
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

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> refreshAccessToken(TokenDTO token, HttpServletResponse response) {

        Message message;

        try {

            String account = jwtUtil.getAccountFromToken(token.getRefreshToken());
            Optional<Users> user = userRepository.findByAccount(account);

            System.out.println("account = " + account);

            if (user.isEmpty()) {
                message = new Message("잘못된 계정 정보입니다.");
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else
                System.out.println("유저 있음");

            if (!jwtUtil.refreshTokenValidation(token.getRefreshToken())) {
                message = new Message("다시 로그인을 해주세요. (refreshToken 검증");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            TokenDTO tokenDto = jwtUtil.createAllToken(user.get().getAccount());
            setHeader(response, tokenDto);

            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsersAccount(account);
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getAccessToken(), tokenDto.getRefreshToken()));

            // 테스트 해보자 이 코드

            return new ResponseEntity<>(tokenDto, HttpStatus.OK);

        } catch (Exception e) {

            message = new Message("다시 로그인을 해주세요. (catch) " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    private void setHeader(HttpServletResponse response, TokenDTO tokenDto) {
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

    public ResponseEntity<Object> update(Users user) {

        Message message;

        try {

            if (user.getId() == null) {

                message = new Message("유저 id가 null");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {

            message = new Message("업데이트 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 비번 찾기
    public ResponseEntity<Object> findIdPw(String account, String phoneNumber) {

        Message message;

        try {

            Optional<Users> byAccountAndPhoneNumber = userRepository.findByAccountAndPhoneNumber(account, phoneNumber);

            if (byAccountAndPhoneNumber.isEmpty()) {
                message = new Message("입력한 정보의 데이터 x");
                return new ResponseEntity<>(message, HttpStatus.OK);

            } else {
                Users user = byAccountAndPhoneNumber.get();
                return new ResponseEntity<>(user, HttpStatus.OK);
            }

        } catch (Exception e) {

            message = new Message("비밀번호 찾기 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 회원 탈퇴
    public ResponseEntity<Object> deleteByUserId(UUID id) {

        Message message;

        try {
            userRepository.deleteById(id);

            message = new Message("회원 탈퇴 완료");
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception e) {
            message = new Message("회원 탈퇴 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 아이디 찾기
    public ResponseEntity<Object> findByNameAndBirth(String name, String birth) {

        Message message;

        try {

            Optional<Users> findUser = userRepository.findByNameAndBirth(name, birth);

            if (findUser.isEmpty()) {
                message = new Message("입력한 정보의 데이터 x");
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {

                Users user = findUser.get();
                return new ResponseEntity<>(user, HttpStatus.OK);
            }

        } catch (Exception e) {

            message = new Message("아이디 찾기 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 유저 개인 정보 가져오기
    public ResponseEntity<Object> getUserInfo(UUID id, UserDetailsImpl userDetails) {

        Message message;

        try {

            Optional<Users> user = userRepository.findByIdAndAccount(id, userDetails.getUser().getAccount());

            if (user.isEmpty()) {

                message = new Message("유저의 id와 account에 따른 계정 정보 X");
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else
                return new ResponseEntity<>(user.get(), HttpStatus.OK);

        } catch (Exception e) {

            message = new Message("getUserInfo() 에러 " + e);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // 유저가 베팅한 거 리스트 가져오기
    public ResponseEntity<Object> getUserBetList(UserDetailsImpl userDetails) {

        try {

            Optional<Users> userGet = userRepository.findById(userDetails.getUser().getId());

            if (userGet.isEmpty())
                return new ResponseEntity<>(new Message("아이디 없음"), HttpStatus.OK);

            Users user = userGet.get();

            List<UserBetPointDTO> userBetPointDTOS = betRepository.findBetsWithPointsByUsersId(user.getId());

            return new ResponseEntity<>(userBetPointDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("getUserBetList error : " + e), HttpStatus.OK);
        }
    }
}
