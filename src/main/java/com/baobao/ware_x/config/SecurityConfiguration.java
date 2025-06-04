package com.baobao.ware_x.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(e -> e.tokenEndpoint(withDefaults()).userInfoEndpoint(withDefaults()));
        http
                .sessionManagement( s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().fullyAuthenticated()
                ).logout(l -> l.logoutSuccessUrl("http://localhost:8080/realms/external/protocol/openid-connect/logout?redirect_uri=http://localhost:8082/"));

        return http.build();
    }
}

