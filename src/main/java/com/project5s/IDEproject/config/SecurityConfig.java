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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                .antMatchers("/user/signUp", "/user/login", "/user/resetPassword").permitAll()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    // 스프링 서버 전역적으로 CORS 설정
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173", "https://localhost:5173", "http://localhost:8080") // 허용할 출처
                    .allowedMethods("GET", "POST") // 허용할 HTTP method
                    .allowCredentials(true) // 쿠키 인증 요청 허용:
                    .maxAge(30000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
        }
    }
}
