package io.allteran.plutos.service;

import io.allteran.plutos.domain.Country;
import io.allteran.plutos.dto.CountryDTO;
import io.allteran.plutos.exception.DuplicateException;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.repo.CountryRepository;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountryService {
    private final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public Flux<Country> findAll() {
        return repository.findAll();
    }

    public Mono<Country> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Country> create(Mono<Country> countryMono) {
        return countryMono
                .flatMap(country -> repository.existsCountryByCode(country.getCode())
                        .flatMap(exists -> (exists) ?
                                Mono.error(new DuplicateException("Can't create Country. Country with CODE [" + country.getCode() + "] already exists in database"))
                                : repository.save(country)));
    }

    public Mono<Country> update(Mono<Country> countryMono, String idFromDb) {
        return countryMono
                .flatMap(country -> repository.findById(idFromDb).flatMap(countryFromDb -> {
                    if(countryFromDb == null) {
                        Mono.error(new NotFoundException("Can't update Country. Country with ID [" + idFromDb + "] not found in DB"));
                    }
                    BeanUtils.copyProperties(country, countryFromDb, "id");
                    return repository.existsCountryByCode(countryFromDb.getCode())
                            .flatMap(exists -> (exists) ?
                                    Mono.error(new DuplicateException("Can't update Country. Country with CODE [" + countryFromDb.getCode() + "] already exists in DB"))
                                    : repository.save(countryFromDb));
                }));
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    public Mono<Boolean> ifExist(String id) {
        return repository.existsCountryById(id);
    }
}
