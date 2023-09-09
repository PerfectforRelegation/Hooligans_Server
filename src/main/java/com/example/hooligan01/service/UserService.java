package com.example.hooligan01.service;

import com.example.hooligan01.entity.Users;
import com.example.hooligan01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> userList() {

        return userRepository.findAll();
    }

    public void join(Users user) {

        Optional<Users> byUserLogId = userRepository.findByUserLogId(user.getUserLogId());

        if (byUserLogId.isEmpty()) {
            userRepository.save(user);
        }
    }

    public Users login(Users user) {

        Optional<Users> byUserLogId = userRepository.findByUserLogId(user.getUserLogId());

        if (byUserLogId.isPresent()) {
            Users userCheck = byUserLogId.get();
            if (userCheck.getUserPassword().equals(user.getUserPassword())) {
                return userCheck;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // 회원 개인 정보
    public Users updateForm(String myLogId) {

        Optional<Users> byUserLogIn = userRepository.findByUserLogId(myLogId);

        return byUserLogIn.get();
    }

    public Boolean update(Users user) {

        userRepository.save(user);

        return true;
    }

    // 아이디/비번 찾기
    public Users findIdPw(String userEmail) {

        Optional<Users> byUserEmail = userRepository.findByUserEmail(userEmail);

        return byUserEmail.orElse(null);
    }

    // 회원 탈퇴
    public void deleteByUserId(Long userId) {
        userRepository.deleteById(userId);
    }
}
