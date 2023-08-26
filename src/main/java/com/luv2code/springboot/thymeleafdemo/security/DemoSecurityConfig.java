package com.luv2code.springboot.thymeleafdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class DemoSecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((autz)->autz
                        .anyRequest().authenticated()
                )
                .formLogin(form->form.permitAll())
                .logout(logout->logout.permitAll());
        return http.build();
    }
}