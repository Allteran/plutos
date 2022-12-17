package io.allteran.plutos.service;

import io.allteran.plutos.dto.PrivilegeDTO;
import io.allteran.plutos.repo.PrivilegeRepository;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PrivilegeService {
    private final PrivilegeRepository repository;

    @Autowired
    public PrivilegeService(PrivilegeRepository repository) {
        this.repository = repository;
    }

    public Flux<PrivilegeDTO> findAll() {
        return repository.findAll().map(EntityMapper::convertToDTO);
    }

    public Mono<PrivilegeDTO> findById(String id) {
        return repository.findById(id)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<PrivilegeDTO> create(Mono<PrivilegeDTO> dtoMono) {
        return dtoMono.map(EntityMapper::convertToEntity)
                .flatMap(repository::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<PrivilegeDTO> update(Mono<PrivilegeDTO> dtoMono, String idFromDb) {
        return repository.findById(idFromDb)
                .flatMap(p -> dtoMono.map(EntityMapper::convertToEntity)
                        .doOnNext(e -> e.setId(idFromDb)))
                .flatMap(repository::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
