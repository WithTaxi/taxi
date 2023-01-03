package com.withtaxi.taxi.config.provider;

import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.config.auth.PrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /***
     * form-data로 전송된 데이터로 회원을 조회하고 회원이 있다면 패스워드를 검증함
     * @param authentication the authentication request object.
     * @return 회원유무 판단 후 회원이 맞다면 토큰 발급
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("CustomAuthentication 실행");

        // 회원이 있다면 loadUserByUserName을 통해서 회원유무 판단 가능
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(authentication.getName());
        String reqPassword = authentication.getCredentials().toString(); // 받아온 값

        if(!passwordEncoder.matches(reqPassword, principalDetails.getPassword())) {
            throw new BadCredentialsException("");
        }


        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
