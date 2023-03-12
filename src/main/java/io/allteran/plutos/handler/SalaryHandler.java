package io.allteran.plutos.handler;

import io.allteran.plutos.dto.ShiftDTO;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.service.ShiftService;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SalaryHandler {
    private final ShiftService shiftService;

    @Autowired
    public SalaryHandler(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<ShiftDTO> salaryList = shiftService.findAll().map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(salaryList, ShiftDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ShiftDTO> findByIdMono = shiftService.findById(id)
                .map(EntityMapper::convertToDTO)
                .switchIfEmpty(Mono.error(new NotFoundException("Can't find Salary with ID [" + id + "]")));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(findByIdMono, ShiftDTO.class);
    }

    public Mono<ServerResponse> findByUser(ServerRequest request) {
        String id = request.queryParam("userId").orElse("");
        Flux<ShiftDTO> findByUserFlux = shiftService.findByUserId(id).map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(findByUserFlux, ShiftDTO.class);
    }

    public Mono<ServerResponse> findByUserDate(ServerRequest request) {
        String userId = request.queryParam("userId").orElse("");
        String timeStringFrom = request.queryParam("from").orElse(LocalDateTime.now().toString());
        String timeStringTo = request.queryParam("to").orElse(LocalDateTime.now().toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeFrom = LocalDateTime.parse(timeStringFrom, formatter);
        LocalDateTime timeTo = LocalDateTime.parse(timeStringTo, formatter);

        Flux<ShiftDTO> searchResult = shiftService.findByUserAndDate(userId, timeFrom, timeTo).map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchResult, ShiftDTO.class);
    }

    public Mono<ServerResponse> findByUserCompany(ServerRequest request) {
        String userId = request.queryParam("userId").orElse("");
        String companyId = request.queryParam("companyId").orElse("");

        Flux<ShiftDTO> searchResult = shiftService.findByUserAndCompany(userId, companyId).map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchResult, ShiftDTO.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<ShiftDTO> body = request.bodyToMono(ShiftDTO.class);
        Mono<ShiftDTO> createdSalaryDTO = shiftService.create(body.map(EntityMapper::convertToEntity))
                .map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdSalaryDTO, ShiftDTO.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idFromDb = request.pathVariable("id");
        Mono<ShiftDTO> body = request.bodyToMono(ShiftDTO.class);
        Mono<ShiftDTO> updatedSalaryDTO = shiftService.update(body.map(EntityMapper::convertToEntity), idFromDb)
                .map(EntityMapper::convertToDTO);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedSalaryDTO, ShiftDTO.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deletetionMono = shiftService.delete(id);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deletetionMono, Void.class);
    }

}
