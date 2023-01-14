package io.allteran.plutos.handler;

import io.allteran.plutos.config.JwtUtil;
import io.allteran.plutos.domain.Role;
import io.allteran.plutos.domain.User;
import io.allteran.plutos.dto.UserDTO;
import io.allteran.plutos.service.UserService;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserHandler(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public Mono<ServerResponse> createAdmin(ServerRequest request) {
        User admin = new User();
        admin.setEmail("vitalii.prozapas@gmail.com");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setPassword("123123123");
        admin.setCountryId("63962d83d94c65354d903ca4");
        admin.setDateOfBirth(LocalDate.of(1994, 3, 25));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_ADMIN);
        roles.add(Role.ROLE_USER);
        admin.setRoles(roles);
        admin.setPrivilegeIds(Set.of("639dbe1805f529224305be95"));
        admin.setActive(true);

        Mono<User> adminMono = Mono.just(admin);

        Mono<UserDTO> createdAdmin = userService.createAdmin(adminMono)
                .map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(createdAdmin, UserDTO.class);
    }

}
