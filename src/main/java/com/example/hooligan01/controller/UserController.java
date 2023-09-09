package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Users;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 유저 리스트
    @GetMapping("/userList")
    public List<Users> userList() {

        return userService.userList();
    }

    // 회원가입
    @PostMapping("/join")
    public String userJoin(@RequestBody Users user) {

        userService.join(user);

        return "sss";
    }

    // 로그인
    @PostMapping("/login")
    public String userLogin(@RequestBody Users user, HttpSession session) {

        Users loginResult = userService.login(user);

        // 로그인 성공
        if (loginResult != null) {

            session.setAttribute("userLogId", loginResult.getUserLogId());
            session.setAttribute("userNick", loginResult.getUserNick());

            return loginResult.getUserLogId();
        } else {
            return null;
        }
    }

    // 아이디 찾기(이메일과 비밀번호를 받음)
    @PostMapping("/findUserId")
    public Users userFindUserId(@RequestBody Users users) {
        Users findIdResult = userService.findIdPw(users.getUserEmail());

        if (findIdResult == null) {
            return null;
        } else if (!findIdResult.getUserPassword().equals(users.getUserPassword())) {
            return null;
        } else
            return findIdResult;

    }

    // 비밀번호 찾기(이메일과 아이디 받음)
    @PostMapping("/findUserPassword")
    public Users userFindUserPw(@RequestBody Users users) {
        Users findIdResult = userService.findIdPw(users.getUserEmail());

        if (findIdResult == null) {
            return null;
        } else if (!findIdResult.getUserLogId().equals(users.getUserLogId())) {
            return null;
        } else
             return findIdResult;

    }


    // 내 정보 수정(정보 받기)
    @GetMapping("/updateForm")
    public Users userUpdateForm(HttpSession session) {

        String myLogId = (String) session.getAttribute("userLogId");

        return userService.updateForm(myLogId);
    }

    // 내 정보 수정(세션 안넣음)
    @PostMapping("/update")
    public Boolean update(@RequestBody Users user) {

        return userService.update(user);
    }

    // 회원 탈퇴
    @GetMapping("/delete/{userId}")
    public Boolean deleteByUserId(@PathVariable Long userId) {

        userService.deleteByUserId(userId);

        return true;
    }

    // 로그아웃
    @GetMapping("/logout")
    public Boolean logout(HttpSession session) {

        session.invalidate();

        return true;
    }

}