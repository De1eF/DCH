package budkevych.dcsapi.config;

import budkevych.dcsapi.security.jwt.JwtTokenFilter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final ConfigProperties addressProvider;

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
        return http
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

                                .requestMatchers("/check-token")
                                .permitAll()

                                .requestMatchers("/swagger-ui/**")
                                .permitAll()

                                .requestMatchers("/swagger-ui")
                                .permitAll()

                                .requestMatchers(HttpMethod.GET, "/users/me")
                                .hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/users/{id}/role")
                                .hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/characters")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.PUT, "/characters/{id}")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.DELETE, "/characters/{id}")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET, "/characters/{id}")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET, "/characters/recover/{id}")
                                .hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/characters/for-user/{id}")
                                .hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET, "/sessions/**")
                                .hasAnyRole("ADMIN", "USER")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                addressProvider.getAddress(),
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
