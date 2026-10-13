package com.ahujanursinghome.hospital_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // ✅ Require authentication for specific endpoints only
                        .requestMatchers(
                                "/api/admin/**",
                                "/api/user/profile",
                                "/api/secure/**",
                                "/departments/upload",
                                "/about-us/upload-banner-image",
                                "/about-us/upload-director",
                                "/about-us/about-journey",
                                "/doctor/uploadDoctorsDetails",
                                "/event/upload-upcoming-event",
                                "/gallery/upload",
                                "/gallery/add-image-category",
                                "/miscellaneous/upload-all-addresses",
                                "/miscellaneous/upload-hero-image",
                                "/miscellaneous/delete-hero-image/{id}",
                                "/miscellaneous/delete-contact/{id}",
                                "/about-us/delete-director/{id}",
                                "/about-us/delete-banner-image/{id}",
                                "/about-us/delete-journey/{id}",
                                "/doctor/remove-doctor/{id}",
                                "/gallery/delete-gallery-image/{id}",
                                "/v1/auth/update-password",
                                "/miscellaneous/delete-contact/{id}",
                                "/miscellaneous/delete-review{id}",
                                "/v1/auth/register"
                        ).authenticated()

                        // ✅ Permit all others
                        .anyRequest().permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOriginPatterns(List.of("http://168.231.123.223:3000")); // ✅ preferred
        config.setAllowedOriginPatterns(List.of("http://localhost:3000")); // ✅ preferred
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false); // ✅ only safe when origin is not "*"

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // ✅ correct path pattern
        return source;
    }




}

