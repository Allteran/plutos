package io.allteran.plutos.handler;

import io.allteran.plutos.dto.PrivilegeDTO;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.plaf.BorderUIResource;

@Component
public class PrivilegeHandler {
    private final PrivilegeService privilegeService;

    @Autowired
    public PrivilegeHandler(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<PrivilegeDTO> findAllFlux = privilegeService.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(findAllFlux, PrivilegeDTO.class));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<PrivilegeDTO> dto = privilegeService.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Can't find Privilege with ID [" + id + "]")));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(dto, PrivilegeDTO.class));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<PrivilegeDTO> body = request.bodyToMono(PrivilegeDTO.class);
        Mono<PrivilegeDTO> createdPrivilege = privilegeService.create(body);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(createdPrivilege, PrivilegeDTO.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idFromDb = request.pathVariable("id");
        Mono<PrivilegeDTO> body = request.bodyToMono(PrivilegeDTO.class);
        Mono<PrivilegeDTO> updatedPrivilegeDTO = privilegeService.update(body, idFromDb).switchIfEmpty(Mono.error(new NotFoundException("Can't find Privilege with ID [" + idFromDb + "]")));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(updatedPrivilegeDTO, PrivilegeDTO.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deletationMono = privilegeService.delete(id).switchIfEmpty(Mono.error(new NotFoundException("Can't find Privilege with ID [" + id + "]")));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(deletationMono, Void.class));
    }
}
