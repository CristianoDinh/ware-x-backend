package com.baobao.ware_x.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .oauth2Client(Customizer.withDefaults())
                .oauth2Login(
//                        e -> e.tokenEndpoint(withDefaults()).userInfoEndpoint(withDefaults())
                        oauth -> oauth
                                .defaultSuccessUrl("http://localhost:4200/ware-x/home", true)
                );
        http
            .sessionManagement( s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                    .anyRequest().fullyAuthenticated()
            ).logout(l -> l.logoutSuccessUrl("http://localhost:8080/realms/external/protocol/openid-connect/logout?redirect_uri=http://localhost:8082/"));

        // Disable CSRF protection
        http.csrf(AbstractHttpConfigurer::disable);
        // Configure CORS settings
    //    http.cors(cors -> cors.configurationSource(request -> corsConfiguration));
        // Customize OAuth2 resource server configuration
        http.oauth2ResourceServer( oauth2 -> oauth2
            .jwt(withDefaults())
        );

        return http.build();
    }
}

