package io.allteran.plutos.service;

import io.allteran.plutos.dto.SalaryDTO;
import io.allteran.plutos.repo.SalaryRepository;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class SalaryService {
    private final SalaryRepository repository;

    @Autowired
    public SalaryService(SalaryRepository repository) {
        this.repository = repository;
    }

    public Flux<SalaryDTO> findAll() {
        return repository.findAll().map(EntityMapper::convertToDTO);
    }

    public Mono<SalaryDTO> findById(String id) {
        return repository.findById(id).map(EntityMapper::convertToDTO);
    }

    public Flux<SalaryDTO> findByUser(String userId) {
        return repository.findAllByUserId(userId).map(EntityMapper::convertToDTO);
    }

    public Flux<SalaryDTO> findByUserAndDate(String userId, LocalDateTime from, LocalDateTime to) {
        return repository.findAllByUserAndDate(userId, from, to).map(EntityMapper::convertToDTO);
    }

    public Flux<SalaryDTO> findByUserAndCompany(String userId, String companyId) {
        return repository.findAllByUserAndCompany(userId, companyId).map(EntityMapper::convertToDTO);
    }

    //TODO: dont forget to add user-existence check and company-existence check. In future
    public Mono<SalaryDTO> create(Mono<SalaryDTO> salaryDTOMono) {
        return salaryDTOMono.map(EntityMapper::convertToEntity)
                .flatMap(repository::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<SalaryDTO> update(Mono<SalaryDTO> salaryDTOMono, String idFromDb) {
        return repository.findById(idFromDb)
                .flatMap(s -> salaryDTOMono.map(EntityMapper::convertToEntity)
                        .doOnNext(e -> e.setId(idFromDb)))
                .flatMap(repository::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
