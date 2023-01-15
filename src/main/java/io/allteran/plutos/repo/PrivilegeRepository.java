package io.allteran.plutos.repo;

import io.allteran.plutos.domain.Privilege;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PrivilegeRepository extends ReactiveMongoRepository<Privilege, String> {
    Mono<Boolean> existsPrivilegeById(String id);
}
