package io.allteran.plutos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                                )
                )
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/", "/auth/login", "/favicon.ico").permitAll()
                //TODO: DO NOT PATH BASED ON USER ROLE
                .pathMatchers("/router/salary").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }
}
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;
//
//@EnableWebFluxSecurity
////@EnableMethodSecurity
//@Configuration
//public class WebSecurityConfig {
//    private final AuthenticationManager authManager;
//    private final SecurityContextRepository securityContextRepo;
//
//    @Autowired
//    public WebSecurityConfig(AuthenticationManager authManager, SecurityContextRepository securityContextRepo) {
//        this.authManager = authManager;
//        this.securityContextRepo = securityContextRepo;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//        return httpSecurity
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (swe, e) ->
//                                Mono.fromRunnable(
//                                        () -> swe
//                                                .getResponse()
//                                                .setStatusCode(HttpStatus.UNAUTHORIZED)
//                                )
//                )
//                .accessDeniedHandler(
//                        (swe, e) ->
//                                Mono.fromRunnable(
//                                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
//                                )
//                ).and()
//                .csrf().disable()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .authenticationManager(authManager)
//                .securityContextRepository(securityContextRepo)
//                .authorizeExchange()
//                .pathMatchers("/", "/auth/login", "favicon.ico").permitAll()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                //next line should uncomment when you will implement access for some role
////                .pathMatchers("/route/admin").hasRole("ADMIN")
//                .anyExchange().authenticated()
//                .and()
//                .build();
//    }
//}
