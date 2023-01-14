package io.allteran.plutos.handler;

import io.allteran.plutos.dto.SalaryDTO;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SalaryHandler {
    private final SalaryService salaryService;

    @Autowired
    public SalaryHandler(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<SalaryDTO> salaryList = salaryService.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(salaryList, SalaryDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<SalaryDTO> findByIdMono = salaryService.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Can't find Salary with ID [" + id + "]")));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(findByIdMono, SalaryDTO.class);
    }

    public Mono<ServerResponse> findByUser(ServerRequest request) {
        String id = request.queryParam("userId").orElse("");
        Flux<SalaryDTO> findByUserFlux = salaryService.findByUser(id);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(findByUserFlux, SalaryDTO.class);
    }

    public Mono<ServerResponse> findByUserDate(ServerRequest request) {
        String userId = request.queryParam("userId").orElse("");
        String timeStringFrom = request.queryParam("from").orElse(LocalDateTime.now().toString());
        String timeStringTo = request.queryParam("to").orElse(LocalDateTime.now().toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeFrom = LocalDateTime.parse(timeStringFrom, formatter);
        LocalDateTime timeTo = LocalDateTime.parse(timeStringTo, formatter);

        Flux<SalaryDTO> searchResult = salaryService.findByUserAndDate(userId, timeFrom, timeTo);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchResult, SalaryDTO.class);
    }

    public Mono<ServerResponse> findByUserCompany(ServerRequest request) {
        String userId = request.queryParam("userId").orElse("");
        String companyId = request.queryParam("companyId").orElse("");

        Flux<SalaryDTO> searchResult = salaryService.findByUserAndCompany(userId, companyId);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchResult, SalaryDTO.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<SalaryDTO> body = request.bodyToMono(SalaryDTO.class);
        Mono<SalaryDTO> createdSalaryDTO = salaryService.create(body);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdSalaryDTO, SalaryDTO.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idFromDb = request.pathVariable("id");
        Mono<SalaryDTO> body = request.bodyToMono(SalaryDTO.class);
        Mono<SalaryDTO> updatedSalaryDTO = salaryService.update(body, idFromDb);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedSalaryDTO, SalaryDTO.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deletetionMono = salaryService.delete(id);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deletetionMono, Void.class);
    }

}
