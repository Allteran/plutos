package io.allteran.plutos.service;

import io.allteran.plutos.domain.Country;
import io.allteran.plutos.dto.CountryDTO;
import io.allteran.plutos.repo.CountryRepository;
import io.allteran.plutos.util.EntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CountryService {
    private final CountryRepository countryRepo;

    @Autowired
    public CountryService(CountryRepository countryRepo) {
        this.countryRepo = countryRepo;
    }

    public Flux<Country> findAll() {
        return countryRepo.findAll();
    }

    public Mono<CountryDTO> findById(String id) {
        return countryRepo.findById(id)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<CountryDTO> create(Mono<CountryDTO> countryDTOMono) {
        return countryDTOMono.map(EntityMapper::convertToEntity)
                .flatMap(countryRepo::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<CountryDTO> update(Mono<CountryDTO> countryDTOMono, String idFromDb) {
        return countryRepo.findById(idFromDb)
                .flatMap(p->countryDTOMono.map(EntityMapper::convertToEntity)
                        .doOnNext(e->e.setId(idFromDb)))
                .flatMap(countryRepo::save)
                .map(EntityMapper::convertToDTO);
    }

    public Mono<Void> delete(String id) {
        return countryRepo.deleteById(id);
    }

    public Mono<Boolean> ifExist(String id) {
        return countryRepo.existsCountryById(id);
    }
}
