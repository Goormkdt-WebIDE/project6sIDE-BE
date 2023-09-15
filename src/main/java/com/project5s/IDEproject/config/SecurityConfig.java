package com.project5s.IDEproject.config;

import com.project5s.IDEproject.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {

        return (web) -> web.ignoring().antMatchers("/api/**", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/users/join", "/api/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
