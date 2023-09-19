package com.example.hooligan01.controller;

import com.example.hooligan01.entity.Users;
import com.example.hooligan01.service.UserService;
import lombok.RequiredArgsConstructor;
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

            session.setAttribute("loginId", loginResult.getLoginId());
            session.setAttribute("nick", loginResult.getNick());

            return loginResult.getLoginId();
        } else {
            return null;
        }
    }

    // 아이디 찾기(이메일과 비밀번호를 받음)
    @PostMapping("/findUserId")
    public Users userFindUserId(@RequestBody Users users) {
        Users findIdResult = userService.findIdPw(users.getEmail());

        if (findIdResult == null) {
            return null;
        } else if (!findIdResult.getPassword().equals(users.getPassword())) {
            return null;
        } else
            return findIdResult;

    }

    // 비밀번호 찾기(이메일과 아이디 받음)
    @PostMapping("/findUserPassword")
    public Users userFindUserPw(@RequestBody Users users) {
        Users findIdResult = userService.findIdPw(users.getEmail());

        if (findIdResult == null) {
            return null;
        } else if (!findIdResult.getLoginId().equals(users.getLoginId())) {
            return null;
        } else
             return findIdResult;

    }


    // 내 정보 수정(정보 받기)
    @GetMapping("/updateForm")
    public Users userUpdateForm(HttpSession session) {

        String myLoginId = (String) session.getAttribute("loginId");

        return userService.updateForm(myLoginId);
    }

    // 내 정보 수정(세션 안넣음)
    @PostMapping("/update")
    public Boolean update(@RequestBody Users user) {

        return userService.update(user);
    }

    // 회원 탈퇴
    @GetMapping("/delete/{id}")
    public Boolean deleteByUserId(@PathVariable Long id) {

        userService.deleteByUserId(id);

        return true;
    }

    // 로그아웃
    @GetMapping("/logout")
    public Boolean logout(HttpSession session) {

        session.invalidate();

        return true;
    }

}