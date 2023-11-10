package com.example.hooligan01.service;

import com.example.hooligan01.dto.BoardsDTO;
import com.example.hooligan01.dto.LoginResponse;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.dto.UserBetPointDTO;
import com.example.hooligan01.entity.*;
import com.example.hooligan01.repository.*;
import com.example.hooligan01.security.JwtUtil;

import com.example.hooligan01.dto.TokenDTO;

import com.example.hooligan01.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BettingRepository bettingRepository;
    private final PointRepository pointRepository;
    private final BoardRepository boardRepository;

    public List<Users> userList() {

        return userRepository.findAll();
    }

//    public ResponseEntity<Object> findById(UUID id) {
//
//        Optional<Users> userGet = userRepository.findById(id);
//
//        return userGet.<ResponseEntity<Object>>map(
//            users -> new ResponseEntity<>(users, HttpStatus.OK)).orElseGet(
//            () -> new ResponseEntity<>(new Message("UserService.findById 아이디 값에 따른 유저 정보 없음"),
//                HttpStatus.OK));
//    }

    public ResponseEntity<Object> join(Users inputUser) {

        try {
            Optional<Users> userGet = userRepository.findByAccount(inputUser.getAccount());
            if (userGet.isPresent())
                return new ResponseEntity<>(new Message("아이디 중복"), HttpStatus.OK);

            Optional<Users> userGet2 = userRepository.findByNickname(inputUser.getNickname());
            if (userGet2.isPresent())
                return new ResponseEntity<>(new Message("닉네임 중복"), HttpStatus.OK);

            inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));

            if (inputUser.getBetPoint() == 0)
                inputUser.setBetPoint(5000);

            userRepository.save(inputUser);

            return new ResponseEntity<>(new Message("회원가입 성공"), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(new Message("회원가입 에러: " + e), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> login(Users inputUser, HttpServletResponse response) {

        try {
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
            if(refreshToken.isEmpty()) {
//                refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
                RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), user);
                refreshTokenRepository.save(newToken);
                user.setToken(newToken);

                // response 헤더에 Access Token / Refresh Token 넣음
                setHeader(response, tokenDto);
            } else {

                TokenDTO dto = TokenDTO.builder()
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(refreshToken.get().getRefreshToken())
                    .build();
                setHeader(response, dto);
            }

            LoginResponse loginResponse =  LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .account(user.getAccount())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .betPoint(user.getBetPoint())
                .favoriteTeam(user.getFavoriteTeam())
                .tokenDto(tokenDto)
                .build();

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("로그인 에러: " + e, HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> refreshAccessToken(TokenDTO token, HttpServletResponse response) {

        Message message;

        try {

            String account = jwtUtil.getAccountFromToken(token.getRefreshToken());
            Optional<Users> user = userRepository.findByAccount(account);

            if (user.isEmpty()) {
                message = new Message("잘못된 계정 정보입니다.");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            if (!jwtUtil.refreshTokenValidation(token.getRefreshToken())) {
                message = new Message("다시 로그인을 해주세요. (refreshToken 검증");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            TokenDTO tokenDto = jwtUtil.createAllToken(user.get().getAccount());
            setHeader(response, tokenDto);

            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsersAccount(account);

            if (refreshToken.isEmpty())
                return new ResponseEntity<>(new Message("계정에 따른 토큰 정보가 없음"), HttpStatus.OK);

            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));

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

    public ResponseEntity<Object> update(Users inputUser) {

        Message message;

        try {

            if (inputUser.getId() == null) {

                message = new Message("유저 id가 null");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }

            Optional<Users> userGet = userRepository.findById(inputUser.getId());

            if (userGet.isEmpty())
                return new ResponseEntity<>(new Message("유저 업데이트 에러: 아이디 값에 따른 유저 정보 없음"), HttpStatus.OK);

            Users user = userGet.get();

            if (inputUser.getNickname() != null) {

                if (userRepository.findByNickname(inputUser.getNickname()).isPresent())
                    return new ResponseEntity<>(new Message("존재하는 닉네임입니다."), HttpStatus.OK);

                user.setNickname(inputUser.getNickname());
            }

            if (inputUser.getPassword() != null)
                user.setPassword(passwordEncoder.encode(inputUser.getPassword()));

            if (inputUser.getPhoneNumber() != null)
                user.setPhoneNumber(inputUser.getPhoneNumber());

            if (inputUser.getFavoriteTeam() != null)
                user.setFavoriteTeam(inputUser.getFavoriteTeam());

            if (inputUser.getFilename() != null)
                user.setFilename(inputUser.getFilename());

            if (inputUser.getFilepath() != null)
                user.setFilepath(inputUser.getFilepath());

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

    // 유저 개인 정보 가져오기 ※
//    public ResponseEntity<Object> getUserInfo(UUID id, UserDetailsImpl userDetails) {
//
//        Message message;
//
//        try {
//
//            Optional<Users> user = userRepository.findByIdAndAccount(id, userDetails.getUser().getAccount());
//
//            if (user.isEmpty()) {
//
//                message = new Message("유저의 id와 account에 따른 계정 정보 X");
//                return new ResponseEntity<>(message, HttpStatus.OK);
//            } else
//                return new ResponseEntity<>(user.get(), HttpStatus.OK);
//
//        } catch (Exception e) {
//
//            message = new Message("getUserInfo() 에러 " + e);
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        }
//    }

    // 유저가 베팅한 거 리스트 가져오기
    public ResponseEntity<Object> getBetList(UserDetailsImpl userDetails) {

        try {
            Optional<Users> userGet = userRepository.findById(userDetails.getUser().getId());

            if (userGet.isEmpty())
                return new ResponseEntity<>(new Message("아이디 없음"), HttpStatus.OK);

            Users user = userGet.get();

            List<Betting> bettingList = bettingRepository.findAllByUsersId(user.getId());

            List<UserBetPointDTO> userBetPointDTOS = new ArrayList<>();

            for (Betting oneBetting : bettingList)
            {
                UserBetPointDTO dto = UserBetPointDTO.builder()
                        .id(oneBetting.getBets().getId())
                        .date(oneBetting.getBets().getFixtures().getDate())
                        .home(oneBetting.getBets().getFixtures().getHome())
                        .away(oneBetting.getBets().getFixtures().getAway())
                        .homeAllocation(oneBetting.getBets().getFixtures().getHomeAllocation())
                        .awayAllocation(oneBetting.getBets().getFixtures().getAwayAllocation())
                        .drawAllocation(oneBetting.getBets().getFixtures().getDrawAllocation())
                        .win(oneBetting.getBets().getWin())
                        .point(pointRepository.findPointsByBetsIdAndUsers(oneBetting.getBets().getId(), user).get())
                        .build();

                userBetPointDTOS.add(dto);
            }

            return new ResponseEntity<>(userBetPointDTOS, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(new Message("UserService.getBetList error : " + e), HttpStatus.BAD_REQUEST);
        }
    }

    // /detail
    public ResponseEntity<Object> getUserDetailList(UserDetailsImpl userDetails) {

        try {
            if (userDetails == null)
                return new ResponseEntity<>(new Message("UserService.getUserDetailList 인증 정보가 없음"), HttpStatus.OK);

            Optional<Users> userGet = userRepository.findByAccount(userDetails.getUser().getAccount());

            if (userGet.isEmpty())
                return new ResponseEntity<>(new Message("UserService.getUserDetailList 유저 정보가 없음"), HttpStatus.OK);

            Users user = userGet.get();

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(new Message("UserService.getUserDetailList error: " + e), HttpStatus.OK);
        }
    }

    // DeleteMapping /
    public ResponseEntity<Object> deleteUser(UserDetailsImpl userDetails) {

        try {
            if (userDetails == null)
                return new ResponseEntity<>(new Message("UserService.deleteUser 인증 정보 없음"), HttpStatus.OK);

            Optional<Users> userGet = userRepository.findByAccount(userDetails.getUser().getAccount());

            if (userGet.isEmpty())
                return new ResponseEntity<>(new Message("UserService.deleteUser error, account 값에 따른 데이터 없음"), HttpStatus.OK);

            Users user = userGet.get();

            userRepository.deleteById(user.getId());

            return new ResponseEntity<>(new Message("회원 탈퇴 완료"), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(new Message("UserService.deleteUser error: " + e), HttpStatus.OK);
        }
    }

    // 유저가 쓴 게시판 리스트
    public ResponseEntity<Object> getBoardList(UserDetailsImpl userDetails) {

        try {
            if (userDetails == null)
                return new ResponseEntity<>(new Message("UserService.getBoardList error: 인증 정보 없음"), HttpStatus.OK);

            Optional<Users> userGet = userRepository.findByAccount(userDetails.getUser().getAccount());

            if (userGet.isEmpty())
                return new ResponseEntity<>(new Message("UserService.getBoardList error: 유저 정보 없음"), HttpStatus.OK);

            List<BoardsDTO> boardsDTOList = boardRepository.findByUserId(userGet.get().getId());

            return new ResponseEntity<>(boardsDTOList, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(new Message("UserService.getBoardList error: " + e), HttpStatus.OK);
        }
    }
}
