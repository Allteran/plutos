package io.allteran.plutos.repo;

import io.allteran.plutos.domain.Shift;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ShiftRepository extends ReactiveMongoRepository<Shift, String> {
//    @Query("{'userId': ?0}")
    Flux<Shift> findAllByUserId(String userId);

    @Query("{'userId' :  ?0, 'shiftStart' : {$gte: ?1, $lte: ?2}}")
    Flux<Shift> findAllByUserAndDate(String userId, LocalDateTime from, LocalDateTime to);

    @Query("{'userId' :  ?0, 'companyId' : ?1}")
    Flux<Shift> findAllByUserAndCompany(String userId, String companyId);


}
