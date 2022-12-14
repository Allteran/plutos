package io.allteran.plutos.handler;

import io.allteran.plutos.dto.CompanyDTO;
import io.allteran.plutos.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CompanyHandler {
    private final CompanyService companyService;

    @Autowired
    public CompanyHandler(CompanyService companyService) {
        this.companyService = companyService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<CompanyDTO> companyDTOFlux = companyService.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(companyDTOFlux, CompanyDTO.class));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<CompanyDTO> companyDTOMono = companyService.findById(id);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(companyDTOMono, CompanyDTO.class));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CompanyDTO> body = request.bodyToMono(CompanyDTO.class);
        Mono<CompanyDTO> createdCompany = companyService.create(body);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(createdCompany, CompanyDTO.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idFromDb = request.pathVariable("id");
        Mono<CompanyDTO> companyDTOMono = request.bodyToMono(CompanyDTO.class);
        Mono<CompanyDTO> updatedDTOMono = companyService.update(companyDTOMono, idFromDb);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(updatedDTOMono, CompanyDTO.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deletationMono = companyService.delete(id);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(deletationMono, Void.class));
    }
}
