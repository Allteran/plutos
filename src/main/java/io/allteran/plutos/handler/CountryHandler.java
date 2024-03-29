package io.allteran.plutos.handler;

import io.allteran.plutos.domain.Country;
import io.allteran.plutos.dto.CountryDTO;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.service.CountryService;
import io.allteran.plutos.util.EntityMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CountryHandler {
    private final CountryService countryService;

    @Autowired
    public CountryHandler(CountryService countryService) {
        this.countryService = countryService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Country> countryFlux = countryService.findAll();
        Flux<CountryDTO> dtoFlux = countryFlux.map(EntityMapper::convertToDTO);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(dtoFlux, CountryDTO.class));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<CountryDTO> dtoMono = countryService.findById(id).map(EntityMapper::convertToDTO)
                .switchIfEmpty(Mono.error(new NotFoundException("Can't find Country with ID [" +  id +"]")));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(dtoMono, CountryDTO.class));
    }

    @SneakyThrows
    public Mono<ServerResponse> create (ServerRequest request) {
        Mono<CountryDTO> body = request.bodyToMono(CountryDTO.class);
        Mono<CountryDTO> createdCountry = countryService.create(body.map(EntityMapper::convertToEntity)).map(EntityMapper::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(createdCountry, CountryDTO.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idFromDb = request.pathVariable("id");
        Mono<CountryDTO> monoDTO = request.bodyToMono(CountryDTO.class);
        Mono<CountryDTO> updatedMonoDTO = countryService.update(monoDTO.map(EntityMapper::convertToEntity), idFromDb)
                .map(EntityMapper::convertToDTO)
                .switchIfEmpty(Mono.error(new NotFoundException("Can't find country with ID [" + idFromDb + "]")));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(updatedMonoDTO, CountryDTO.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deletationMono = countryService.delete(id).switchIfEmpty(Mono.error(new NotFoundException("Can't find Country with ID [" + id + "]")));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(deletationMono, Void.class));
    }
}
