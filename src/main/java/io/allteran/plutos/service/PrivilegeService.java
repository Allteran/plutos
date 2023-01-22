package io.allteran.plutos.service;

import io.allteran.plutos.domain.Privilege;
import io.allteran.plutos.dto.PrivilegeDTO;
import io.allteran.plutos.exception.NotFoundException;
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

    public Flux<Privilege> findAll() {
        return repository.findAll();
    }

    public Mono<Privilege> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Boolean> ifExists(String id) {
        return repository.existsPrivilegeById(id);
    }

    public Mono<Privilege> create(Mono<Privilege> dtoMono) {
        return dtoMono.flatMap(repository::save);
    }

    public Mono<Privilege> update(Mono<Privilege> dtoMono, String idFromDb) {
        return dtoMono
                .flatMap(privilege -> repository.findById(idFromDb).flatMap(privilegeFromDb -> {
                    if(privilegeFromDb == null) {
                        return Mono.error(new NotFoundException("Can't update Privilege. Privilege with ID [" + idFromDb + "] not found in DB"));
                    }
                    privilege.setId(idFromDb);
                    return repository.save(privilege);
                }));
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
