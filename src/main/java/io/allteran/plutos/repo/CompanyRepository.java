package io.allteran.plutos.repo;

import io.allteran.plutos.domain.Company;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyRepository extends ReactiveMongoRepository<Company, String> {
    Mono<Boolean> existsById(String id);

    Flux<Company> findCompaniesByType(String type);
}
