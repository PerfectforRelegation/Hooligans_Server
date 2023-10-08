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
    @GetMapping("/list")
    public List<Users> userList() {

        return userService.userList();
    }

    /***/
    // 유저 한 명의 정보
    @GetMapping("/{id}")
    public Users userInfo(@PathVariable Long id) {

        return userService.findById(id);
    }
    /***/

    // 유저 디테일
    @GetMapping("/detail/{id}")
    public Users userDetail(@PathVariable Long id, HttpSession session) {

        Users user = userService.findById(id);

        String myAccount = (String) session.getAttribute("account");

        if (user == null)
            throw new IllegalArgumentException("아이디 x");
        else if (!user.getAccount().equals(myAccount))
            throw new IllegalArgumentException("계정이 세션값과 불일치");
        else
            return user;
    }

    // 회원가입
    @PostMapping("/join")
    public Boolean userJoin(@RequestBody Users user) {

        return userService.join(user);
    }

    // 로그인
    @PostMapping("/login")
    public Users userLogin(@RequestBody Users user, HttpSession session) {

        Users loginResult = userService.login(user);

        // 로그인 성공
        if (loginResult != null) {

            session.setAttribute("account", loginResult.getAccount());
            session.setAttribute("nickname", loginResult.getNickname());

            return loginResult;
        } else {
            return null;
        }
    }

    // 아이디 찾기(이메일과 비밀번호를 받음)
    // 수정 필요!!
    @PostMapping("/findId")
    public Users userFindUserId(@RequestBody Users users) {
        Users findIdResult = userService.findIdPw(users.getAccount());

        if (findIdResult == null) {
            return null;
        } else if (!findIdResult.getPassword().equals(users.getPassword())) {
            return null;
        } else
            return findIdResult;

    }

    // 비밀번호 찾기(이메일과 전화번호를 받음)
    // 수정 필요!!
    @PostMapping("/findPassword")
    public Users userFindUserPw(@RequestBody Users users) {
        Users findIdResult = userService.findIdPw(users.getAccount());

        if (findIdResult == null) {
            return null;
        } else if (!findIdResult.getPhoneNumber().equals(users.getPhoneNumber())) {
            return null;
        } else
             return findIdResult;

    }

//    // 내 정보 수정(정보 받기)
//    @GetMapping("/updateForm")
//    public Users userUpdateForm(HttpSession session) {
//
//        String myAccount = (String) session.getAttribute("account");
//
//        return userService.updateForm(myAccount);
//    }

    // 내 정보 수정(세션 안넣음)
    @PutMapping("/update")
    public Boolean update(@RequestBody Users user) {

        return userService.update(user);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
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