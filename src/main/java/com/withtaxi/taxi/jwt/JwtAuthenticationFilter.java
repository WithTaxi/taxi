package com.withtaxi.taxi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.handler.CustomAuthenticationFailure;
import com.withtaxi.taxi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login으로 오면 일로들어옴
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            System.out.println("로그인 완료");

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // hash방식으로 작동
        String jwtToken = JWT.create()
                .withSubject("TaxiProjectToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 100))) // 1분
                .withClaim("id", principalDetails.getUser().getUserId())
                .withClaim("name", principalDetails.getUser().getName())
                .sign(Algorithm.HMAC512("gongdeok is soooooo cute"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
        response.getWriter().write(new ObjectMapper().writeValueAsString("Bearer " + jwtToken));

    }
}
