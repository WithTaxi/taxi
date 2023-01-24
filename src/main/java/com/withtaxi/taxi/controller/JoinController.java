package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/join")
public class JoinController {

    private final JoinService joinService;


    /***
     * 회원가입 API
     * @param user
     * @return db에 값 저장
     */
    @PostMapping("")
    public User registerUser(@RequestBody User user) {
        return joinService.registerUser(user);
    }

    @GetMapping("/user-id/{userId}/dup")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(joinService.checkUserIdDuplicate(userId));
    }

    @GetMapping("/user-nickname/{nickName}/dup")
    public ResponseEntity<Boolean> checkNickNameDuplicate(@PathVariable String nickName) {
        return ResponseEntity.ok(joinService.checkNickNameDuplicate(nickName));
    }
}
