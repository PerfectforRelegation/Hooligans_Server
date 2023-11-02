package com.example.hooligan01.controller;
import com.example.hooligan01.dto.Message;
import com.example.hooligan01.dto.TokenDTO;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.security.UserDetailsImpl;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 유저 리스트
    @GetMapping("/list")
    public List<Users> userList() {

        return userService.userList();
    }

    // 회원가입
    @PostMapping("/join")
    public Message userJoin(@RequestBody Users user) throws Exception {

        return userService.join(user);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody Users user, HttpServletResponse response) {

        return userService.login(user, response);
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Object> getRefreshToken(@RequestBody TokenDTO tokenDto, HttpServletResponse response) {

        return userService.refreshAccessToken(tokenDto, response);
    }

    // 아이디 찾기(이름과 생일를 받음)
    // 수정 필요!!
    @PostMapping("/findId")
    public ResponseEntity<Object> userFindUserId(@RequestBody Users user) {

        return userService.findByNameAndBirth(user.getName(), user.getBirth());
    }

    // 비밀번호 찾기(이메일과 전화번호를 받음)
    // 수정 필요!!
    @PostMapping("/findPassword")
    public ResponseEntity<Object> userFindUserPw(@RequestBody Users users) {

        return userService.findIdPw(users.getAccount(), users.getPhoneNumber());
    }

    /***/
    // 유저 한 명의 정보
    @GetMapping("/{id}")
    public Users userInfo(@PathVariable UUID id) {

        return userService.findById(id);
    }
    /***/

    // 유저 디테일
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> userDetail(@PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userService.getUserInfo(id, userDetails);
    }

    // 내 정보 수정
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Users user) {

        return userService.update(user);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteByUserId(@PathVariable UUID id) {

        return userService.deleteByUserId(id);
    }

    // 로그아웃
    @GetMapping("/logout")
    public Boolean logout(HttpSession session) {

        session.invalidate();

        return true;
    }

    // 유저가 베팅한 거 리스트
    @GetMapping("/bet")
    public ResponseEntity<Object> getUserBetList(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userService.getUserBetList(userDetails);
    }
}