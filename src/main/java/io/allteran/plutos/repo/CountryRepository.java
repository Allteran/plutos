package io.allteran.plutos.repo;

import io.allteran.plutos.domain.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CountryRepository extends ReactiveMongoRepository<Country, String> {
    Mono<Boolean> existsCountryById(String id);
}
