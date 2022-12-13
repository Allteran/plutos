package io.allteran.plutos.service;

import io.allteran.plutos.dto.CompanyDTO;
import io.allteran.plutos.repo.CompanyRepository;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompanyService {
    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public Flux<CompanyDTO> findAll() {
        return repository.findAll()
                .map(EntityMapper::convertToDTO);
    }

    public Mono<CompanyDTO> findById(String id) {
        return repository.findById(id)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<CompanyDTO> create(Mono<CompanyDTO> dtoMono) {
        System.out.println("inside create method of CompanyService");
        return dtoMono.map(EntityMapper::convertToEntity)
                .flatMap(repository::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<CompanyDTO> update(Mono<CompanyDTO> dtoMono, String idFromDb) {
        return repository.findById(idFromDb)
                .flatMap(co -> dtoMono.map(EntityMapper::convertToEntity)
                        .doOnNext(e-> e.setId(idFromDb)))
                .flatMap(repository::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
