package com.withtaxi.taxi.controller;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /***
     * user 정보 조회 API
     * @param principalDetails
     * @return
     */
    @GetMapping("/user")
    public @ResponseBody User user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getUser();
    }

    /***
     * 일반 로그인
     * @return
     */
    @GetMapping("/loginForm") // SecurityConfig 파일을 작성하면 스프링시큐리티의 자동 login 페이지 사용 불가능
    public String loginForm() {
        return "loginForm";
    }

    /***
     * 회원가입 홈페이지 이동
     * @return
     */
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);

        return "redirect:/loginForm";
    }
}