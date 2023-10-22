package com.example.hooligan01.service;

import com.example.hooligan01.dto.SignResponse;
import com.example.hooligan01.entity.Authorities;
import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.UserRepository;
import com.example.hooligan01.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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

    public Boolean join(Users user) {

        Optional<Users> byUserAccount = userRepository.findByAccount(user.getAccount());
        Optional<Users> byUserNickname = userRepository.findByNickname(user.getNickname());

        if (byUserAccount.isEmpty() && byUserNickname.isEmpty()) {
            userRepository.save(user);
            return true;
        } else
            return false;   // 어카운트 이미 존재
    }

    public Users login(Users user) {

        Optional<Users> byUserAccount = userRepository.findByAccount(user.getAccount());

        if (byUserAccount.isPresent()) {
            Users userCheck = byUserAccount.get();
            if (userCheck.getPassword().equals(user.getPassword())) {
                return userCheck;
            } else {
                return null;
            }
        } else {
            return null;
        }
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

    // test ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    public boolean joinTest(Users user) throws Exception {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // 주어진 요소로 구성된 불변 리스트를 생성할 때 사용(Collections.singletonList())
            // 이 리스트는 변경할 수 없고, 주로 하나의 요소를 가지는 간단한 리스트를 만들 때 사용
            user.setRoles(Collections.singletonList(Authorities.builder().name("ROLE_USER").build()));

            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }

        return true;
    }

    public SignResponse loginTest(Users user) throws Exception {
        Users userGet = userRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(user.getPassword(), userGet.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponse.builder()
                .id(userGet.getId())
                .name(userGet.getName())
                .account(userGet.getAccount())
                .password(userGet.getPassword())
                .nickname(userGet.getNickname())
                .phoneNumber(userGet.getPhoneNumber())
                .birth(userGet.getBirth())
                .betPoint(userGet.getBetPoint())
                .roles(userGet.getRoles())
                .token(jwtProvider.createToken(userGet.getAccount(), userGet.getRoles()))
                .build();
    }


}
