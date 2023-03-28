package io.allteran.plutos.handler;

import io.allteran.plutos.domain.Company;
import io.allteran.plutos.dto.CompanyDTO;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.service.CompanyService;
import io.allteran.plutos.util.EntityMapper;
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
        Flux<CompanyDTO> companyDTOFlux = companyService.findAll().map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(companyDTOFlux, CompanyDTO.class));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<CompanyDTO> companyDTOMono = companyService.findById(id).map(EntityMapper::convertToDTO)
                .switchIfEmpty(Mono.error(new NotFoundException("Can't find company with ID [" + id + "]")));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(companyDTOMono, CompanyDTO.class));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CompanyDTO> body = request.bodyToMono(CompanyDTO.class);
        Mono<CompanyDTO> createdCompany = companyService.create(body.map(EntityMapper::convertToEntity))
                .map(EntityMapper::convertToDTO);


        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(createdCompany, CompanyDTO.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idFromDb = request.pathVariable("id");
        Mono<CompanyDTO> companyDTOMono = request.bodyToMono(CompanyDTO.class);
        Mono<CompanyDTO> updatedDTOMono = companyService.update(companyDTOMono.map(EntityMapper::convertToEntity), idFromDb)
                .map(EntityMapper::convertToDTO).switchIfEmpty(Mono.error(new NotFoundException("Can't find company with ID [" + idFromDb + "]")));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(updatedDTOMono, CompanyDTO.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deletationMono = companyService.delete(id).switchIfEmpty(Mono.error(new NotFoundException("Can't find company with ID [" + id + "]")));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(deletationMono, Void.class));
    }

    public Mono<ServerResponse> searchByType(ServerRequest request) {
        String type = request.queryParam("type").orElse("");
        Flux<CompanyDTO> searchResult = companyService.findByType(type).map(EntityMapper::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(searchResult, CompanyDTO.class));
    }

}
