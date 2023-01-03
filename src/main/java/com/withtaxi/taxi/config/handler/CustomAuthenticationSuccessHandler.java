package com.withtaxi.taxi.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/***
 * 로그인 인증 성공 후 핸들링 하는 클래스
 * 유저는 localhost:8080/login으로 아이디와 비밀번호를 전송하고
 * 성공하면 현재 클래스가 응답해서 마지막 sendRedirect가 발생함.
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60);
        response.sendRedirect("http://localhost:8080/login/success");
    }
}
