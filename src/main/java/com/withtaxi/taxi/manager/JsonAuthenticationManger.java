package com.withtaxi.taxi.manager;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.config.auth.PrincipalDetailsService;
import com.withtaxi.taxi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonAuthenticationManger implements AuthenticationManager {

    private final PrincipalDetailsService principalDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("JsonAuthenticationManager 이 동작합니다.");

        String userId = authentication.getName();
        System.out.println("userId = " + userId);
        String reqPassword = authentication.getCredentials().toString(); // 받아온 값
        System.out.println("reqPassword = " + reqPassword);
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(userId);

        if(!passwordEncoder.matches(reqPassword, principalDetails.getPassword())) {
            throw new BadCredentialsException("password is not matched");
        }


        return new UsernamePasswordAuthenticationToken(principalDetails
                , principalDetails.getPassword()
                , principalDetails.getAuthorities());
    }
}
