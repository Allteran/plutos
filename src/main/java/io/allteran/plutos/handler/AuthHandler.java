package io.allteran.plutos.handler;

import io.allteran.plutos.config.JwtUtil;
import io.allteran.plutos.domain.User;
import io.allteran.plutos.dto.AuthRequest;
import io.allteran.plutos.dto.AuthResponse;
import io.allteran.plutos.service.UserService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AuthHandler(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<AuthRequest> loginRequest = request.bodyToMono(AuthRequest.class);
        return loginRequest.flatMap(login -> userService.findByUsername(login.getLogin())
                .flatMap(user -> {
                    if (login.getPassword().equals(user.getPassword())) {
                        return ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromValue(
                                                new AuthResponse(user.getUsername(), jwtUtil.generateToken((User) user))));
                    } else {
                        return ServerResponse
                                .badRequest()
                                .body(BodyInserters.fromValue(new ApiResponse()));
                    }
                }).switchIfEmpty(
                        ServerResponse
                                .badRequest()
                                .body(BodyInserters.fromValue(new ApiResponse()))));
    }

}
