package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * User 관련 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    /***
     * user 정보 조회 API
     * 내정보
     * @param principalDetails
     * @return principalDetails.getUser()
     */
    @GetMapping("/info")
    public User user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getUser();
    }

//    @PutMapping("/{userId}")
//    public void modifyUser(@PathVariable String userId, @RequestBody User user) {
//        userService.modifyUser(userId, user);
//    }

    /***
     * 회원 탈퇴 API
     * @param userId
     */
    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable String userId) {
        userService.removeUser(userId);
    }

    /***
     * 아이디 찾기 API
     * 없는 아이디였으면 "존재하지 않는 회원 정보입니다 "
     * @param user
     * @return
     */
    @PostMapping("/findId")
    public String findId(@RequestBody User user) {
        String name = user.getName();
        String email = user.getEmail();

        try {
            User result = userService.findId(name, email);
            return result.getUserId();
        } catch (NullPointerException e) {
            return "존재하지 않는 회원 정보입니다.";
        }
    }

    /***
     * 01/26 할일 비밀번호 변경!
     */
//    @PutMapping("/changePassword")
//    public String changePassword(@) {
//    }
}