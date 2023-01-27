package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * 아이디 찾기
     * @param user
     * @return 일치하는 아이디가 있으면 아이디 반환
     *         일치하는 아이디가 없으면 NPE
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
     * 비밀번호 재확인
     * @param passwordMap
     * @param principalDetails
     * @return 비밀번호 일치시 1
     *         비밀번호 불일치시 0 반환
     *         +
     *         httpStatus 200 ok 반환
     */
    @GetMapping("/checkPassword")
    public ResponseEntity<Integer> checkPassword(@RequestBody Map<String, String> passwordMap, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseEntity(userService.checkPassword(passwordMap.get("password"), principalDetails), HttpStatus.OK);
    }

    /***
     * 비밀번호 수정
     * @param passwordMap
     * @param principalDetails
     * @return 비밀번호 변경 후 1 반환
     *         +
     *         httpStatus 200 ok 반환
     */
    @PutMapping("/modifyPassword")
    public ResponseEntity<Integer> modifyPassword(@RequestBody Map<String, String> passwordMap, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        return new ResponseEntity(userService.modifyUserPassword(passwordMap.get("password"), principalDetails), HttpStatus.OK);
    }

    /***
     * 회원정보 수정
     * @param user
     * @param principalDetails
     * @return 닉네임, 모바일, 이메일, 학교 변경
     *         변경 완료시 1 반환
     */
    @PutMapping("/modifyUserInfo")
    public ResponseEntity<Integer> modifyUserInfo(@RequestBody User user, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseEntity(userService.modifyUserInformation(principalDetails, user), HttpStatus.OK);
    }
}