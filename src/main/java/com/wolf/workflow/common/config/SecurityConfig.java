package com.wolf.workflow.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolf.workflow.common.security.AuthenticationUserService;
import com.wolf.workflow.common.security.jwt.CustomAccessDeniedHandler;
import com.wolf.workflow.common.security.jwt.CustomAuthenticationEntryPoint;
import com.wolf.workflow.common.security.jwt.JwtAuthenticationFilter;
import com.wolf.workflow.common.security.jwt.JwtAuthorizationFilter;
import com.wolf.workflow.common.security.jwt.JwtLogoutHandler;
import com.wolf.workflow.common.security.jwt.JwtLogoutSuccessHandler;
import com.wolf.workflow.common.security.jwt.JwtUtil;
import com.wolf.workflow.user.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity //접근 권한
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationUserService authenticationUserService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    private final JwtLogoutHandler jwtLogoutHandler;
    private final UserAdapter userAdapter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(objectMapper, jwtUtil,
                userAdapter);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, authenticationUserService, userAdapter);
    }

    // CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        // CORS
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/reissue").permitAll()
                .requestMatchers("/cards/**").permitAll() // 로그인 페이지가 없어서 프론트 임시 허용
                .requestMatchers("/columns/**").permitAll() // 로그인 페이지가 없어서 프론트 임시 허용
                .requestMatchers("/boards/**").permitAll()// 로그인 페이지가 없어서 프론트 임시 허용
                .requestMatchers("/", "/login", "/users/login").permitAll()
                .anyRequest().authenticated()
        );

        //security filter
        http.addFilterBefore(jwtAuthorizationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        //로그아웃
        http.logout(logout ->
                logout.logoutUrl("/users/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessHandler(jwtLogoutSuccessHandler)
        );

        //예외 검증
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
        );

        return http.build();
    }
}