package io.allteran.plutos.handler;

import io.allteran.plutos.config.JwtUtil;
import io.allteran.plutos.domain.User;
import io.allteran.plutos.dto.AuthRequest;
import io.allteran.plutos.dto.AuthResponse;
import io.allteran.plutos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class AuthHandler {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Value("${message.login.success}")
    private String MESSAGE_AUTH_SUCCESS;
    @Value("${message.login.fail}")
    private String MESSAGE_AUTH_FAIL;

    @Autowired
    public AuthHandler(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<AuthRequest> loginRequest = request.bodyToMono(AuthRequest.class);
        return loginRequest.flatMap(login -> userService.findByUsername(login.getLogin())
                .flatMap(user -> {
                    if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                        return ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromValue(
                                                new AuthResponse(user.getUsername(), jwtUtil.generateToken((User) user), MESSAGE_AUTH_SUCCESS)));
                    } else {
                        return ServerResponse
                                .badRequest()
                                .body(BodyInserters.fromValue(
                                        new AuthResponse(user.getUsername(), null, MESSAGE_AUTH_FAIL)));
                    }
                }).switchIfEmpty(
                        ServerResponse
                                .badRequest()
                                .body(BodyInserters.fromValue(new AuthResponse(null, null, MESSAGE_AUTH_FAIL)))));
    }

}
