package com.withtaxi.taxi.config;

import com.withtaxi.taxi.config.oauth.PrincipalOauth2UserService;
import com.withtaxi.taxi.filter.JsonUsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록되도록 함
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        http.authorizeHttpRequests()
                .antMatchers("/api/**").hasAnyRole("USER")
                .antMatchers("/chat/**").hasAnyRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
                .addFilterBefore(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true);
        return http.build();
    }
}
