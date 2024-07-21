package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringBootSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**").hasRole("USER")
                .requestMatchers("/user/**").hasRole("ADMIN")
                .requestMatchers("/", "/app/**", "/css/**", "/images/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            );

        http
            .formLogin(login -> login
                .defaultSuccessUrl("/bidList/list")
                .permitAll()
            );

        http
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"))
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}
