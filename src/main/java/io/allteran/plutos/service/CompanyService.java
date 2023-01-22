package io.allteran.plutos.service;

import io.allteran.plutos.domain.Company;
import io.allteran.plutos.dto.CompanyDTO;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.repo.CompanyRepository;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.BeanUtils;
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

    public Flux<Company> findAll() {
        return repository.findAll();
    }

    public Mono<Company> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Company> create(Mono<Company> companyMono) {
        return companyMono.flatMap(repository::save);
    }

    public Mono<Company> update(Mono<Company> companyMono, String idFromDb) {
        return companyMono
                .flatMap(company -> repository.findById(idFromDb).flatMap(companyFromDb -> {
                    if(companyFromDb == null) {
                        Mono.error(new NotFoundException("Can't update Company: Company with ID [" + idFromDb + "] not found in DB"));
                    }
                    BeanUtils.copyProperties(company, companyFromDb, "id");
                    return repository.save(companyFromDb);
                }));
    }

    public Mono<Boolean> ifExists(String id) {
        return repository.existsById(id);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
