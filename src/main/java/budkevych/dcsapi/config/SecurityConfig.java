package budkevych.dcsapi.config;

import budkevych.dcsapi.exception.handler.ExceptionHandlerFilter;
import budkevych.dcsapi.security.jwt.JwtTokenFilter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${frontend.address}")
    private String frontAddress;

    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                                .requestMatchers("/register")
                                .permitAll()

                                .requestMatchers("/login")
                                .permitAll()

                                .requestMatchers("/login-email")
                                .permitAll()

                                .requestMatchers("/swagger-ui/**")
                                .permitAll()

                                .requestMatchers("/swagger-ui")
                                .permitAll()

                                .requestMatchers("/v3/api-docs/**")
                                .permitAll()

                                .requestMatchers("/check-token")
                                .hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.GET, "/users/**")
                                .hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/users/{id}/role")
                                .hasRole("ADMIN")

                                .requestMatchers("/characters/**")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET, "/characters/recover/{id}")
                                .hasRole("ADMIN")

                                .requestMatchers("/sessions/**")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers("/portraits/**")
                                .hasAnyRole("ADMIN", "USER")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenFilter, ExceptionHandlerFilter.class)
                .userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                frontAddress,
                "http://127.0.0.1:5500"));
        configuration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "authorization",
                "content-type",
                "Access-Control-Allow-Origin"));
        configuration.setExposedHeaders(List.of("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
