package io.allteran.plutos.repo;

import io.allteran.plutos.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    @Query("{'email':  ?0}")
    Mono<User> findByEmail(String email);
//    @Query("{'email': ?0}")
//    Mono<UserDetails> findByUsername(String username);

    @Query("{'firstName':  ?0}")
    Flux<User> findByFirstName(String firstName);

    @Query("{'lastName': ?0}")
    Flux<User> findByLastName(String lastName);

    @Query("{'occupation': ?0}")
    Flux<User> findByOccupation(String occupation);

    @Query("{'countryId': ?0}")
    Flux<User> findByCountry(String countryId);

}
