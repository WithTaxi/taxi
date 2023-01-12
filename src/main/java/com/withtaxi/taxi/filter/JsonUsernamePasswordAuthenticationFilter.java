package com.withtaxi.taxi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withtaxi.taxi.config.auth.PrincipalDetails;
import com.withtaxi.taxi.config.handler.CustomAuthenticationFailureHandler;
import com.withtaxi.taxi.config.handler.CustomAuthenticationSuccessHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "userId";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String HTTP_METHOD = "POST";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", HTTP_METHOD);

    @Autowired
    public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper,
                                                    AuthenticationManager authenticationManager,
                                                    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                                                    CustomAuthenticationFailureHandler customAuthenticationFailureHandler)
    {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.objectMapper = objectMapper;
        setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        setAuthenticationFailureHandler(customAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("JsonUsernamePasswordAuthenticationFilter 이 동작한다!!!!!!!!!");

        if (!request.getMethod().equals(HTTP_METHOD) || !request.getContentType().equals("application/json")) {
            log.error("POST 요청이 아니거나 JSON이 아닙니다!");
            throw new AuthenticationServiceException("Authentication method not supported : " + request.getMethod());
        }
        LoginDTO loginDTO = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), LoginDTO.class);

        String userId = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if (userId == null || password == null) {
            throw new AuthenticationServiceException("DATA IS MISS");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, password);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest); // getAuthenticationManager를 커스텀함
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Data
    private static class LoginDTO{
        String username;
        String password;
    }
}
