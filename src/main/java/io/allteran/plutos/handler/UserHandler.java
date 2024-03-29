package io.allteran.plutos.handler;

import io.allteran.plutos.config.JwtUtil;
import io.allteran.plutos.domain.Role;
import io.allteran.plutos.dto.UserDTO;
import io.allteran.plutos.dto.Views;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.exception.TokenException;
import io.allteran.plutos.service.UserService;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserHandler(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<UserDTO> userList = userService.findAll().map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(userList, UserDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<UserDTO> findByIdMono = userService.findById(id).map(EntityMapper::convertToDTO).switchIfEmpty(
                Mono.error(new NotFoundException("Can't find USER with ID [" + id + "]")));

        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(findByIdMono, UserDTO.class);
    }

    public Mono<ServerResponse> findByEmail(ServerRequest request) {
        String email = request.queryParam("email").orElse("");
        Mono<UserDTO> findByEmailMono = userService.findByEmail(email).map(EntityMapper::convertToDTO).switchIfEmpty(
                Mono.error(new NotFoundException("Can't find USER with Email [" + email + "]")));
        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(findByEmailMono, UserDTO.class);
    }

    public Mono<ServerResponse> findByFirstName(ServerRequest request) {
        String firstName = request.queryParam("firstName").orElse("");
        Flux<UserDTO> searchResult = userService.findByFirstName(firstName).map(EntityMapper::convertToDTO).switchIfEmpty(
                Mono.error(new NotFoundException("Can't find any USER with FIRST NAME [" + firstName + "]"))
        );

        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(searchResult, UserDTO.class);
    }

    public Mono<ServerResponse> findByLastName(ServerRequest request) {
        String lastName = request.queryParam("lastName").orElse("");
        Flux<UserDTO> searchResult = userService.findByLastName(lastName).map(EntityMapper::convertToDTO).switchIfEmpty(
                Mono.error(new NotFoundException("Can't find any USER with LAST NAME [" + lastName + "]"))
        );

        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(searchResult, UserDTO.class);
    }

    public Mono<ServerResponse> findByCountry(ServerRequest request) {
        String countryId = request.queryParam("countryId").orElse("");
        Flux<UserDTO> searchResult = userService.findByCountry(countryId).map(EntityMapper::convertToDTO).switchIfEmpty(
                Mono.error(new NotFoundException("Can't find any USER with COUNTRY ID [" + countryId + "]"))
        );

        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(searchResult, UserDTO.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<UserDTO> body = request.bodyToMono(UserDTO.class);
        Mono<UserDTO> createdUserDTO = userService.create(body.map(EntityMapper::convertToEntity)).map(EntityMapper::convertToDTO);

        return ServerResponse
                .ok()
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Public.class)
                .contentType(APPLICATION_JSON)
                .body(createdUserDTO, UserDTO.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<UserDTO> body = request.bodyToMono(UserDTO.class);
        String idFromDb = request.pathVariable("id");

        Mono<UserDTO> updatedUser = userService.update(idFromDb, body.map(EntityMapper::convertToEntity)).map(EntityMapper::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Internal.class)
                .body(updatedUser, UserDTO.class);
    }

//    public Mono<ServerResponse> createAdmin(ServerRequest request) {
//        UserDTO admin = new UserDTO();
//        admin.setEmail("vitalii.prozapas@gmail.com");
//        admin.setPassword(passwordEncoder.encode("123123123"));
//        admin.setFirstName("Real");
//        admin.setLastName("Admin");
//        admin.setDateOfBirth(LocalDate.of(1994,3, 25));
//        admin.setCountryId("6445bcab793b2d2d6c2c807a");
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.ROLE_ADMIN);
//        roles.add(Role.ROLE_USER);
//        admin.setRoles(roles);
//        admin.setPrivilegeIds(Collections.singleton("6445bc59793b2d2d6c2c8079"));
//        admin.setActive(true);
//        admin.setRatePerHour(18);
//        admin.setEmployerId("6445bc03793b2d2d6c2c8078");
//        Mono<UserDTO> createdAdmin = userService
//                .createAdmin(Mono.just(admin).map(EntityMapper::convertToEntity))
//                .map(EntityMapper::convertToDTO);
//        return ServerResponse
//                .ok()
//                .contentType(APPLICATION_JSON)
//                .body(createdAdmin, UserDTO.class);
//    }

    public Mono<ServerResponse> updateUserRaw(ServerRequest request) {
        Mono<UserDTO> body = request.bodyToMono(UserDTO.class);
        String idFromDb = request.pathVariable("id");

        Mono<UserDTO> updatedUser = userService.updateRaw(idFromDb, body.map(EntityMapper::convertToEntity)).map(EntityMapper::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .hint(Jackson2CodecSupport.JSON_VIEW_HINT, Views.Internal.class)
                .body(updatedUser, UserDTO.class);
    }

    public Mono<ServerResponse> getProfile(ServerRequest request) {
        String token = request.queryParam("token").orElse("");
        if(!jwtUtil.validateToken(token)) {
            return Mono.error(new TokenException("Token is wrong or expired"));
        }
        String email = jwtUtil.extractUsername(token);
        Mono<UserDTO> profile = userService.findByEmail(email)
                .map(EntityMapper::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(profile, UserDTO.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> userDeleted = userService.delete(id);

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(userDeleted, Void.class);
    }
}
