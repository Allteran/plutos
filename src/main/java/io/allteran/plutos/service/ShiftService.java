package io.allteran.plutos.service;

import io.allteran.plutos.domain.Shift;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.repo.ShiftRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShiftService {
    private final ShiftRepository repository;
    private final CompanyService companyService;
    private final UserService userService;

    @Autowired
    public ShiftService(ShiftRepository repository, CompanyService companyService, UserService userService) {
        this.repository = repository;
        this.companyService = companyService;
        this.userService = userService;
    }


    public Flux<Shift> findAll() {
        return repository.findAll();
    }

    public Mono<Shift> findById(String id) {
        return repository.findById(id);
    }

    public Flux<Shift> findByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    public Flux<Shift> findByUserAndDate(String userId, LocalDateTime from, LocalDateTime to) {
        return repository.findAllByUserAndDate(userId, from, to);
    }

    public Flux<Shift> findByUserAndCompany(String userId, String companyId) {
        return repository.findAllByUserAndCompany(userId, companyId);
    }

    public Mono<Shift> create(Mono<Shift> shiftMono) {
        return shiftMono.flatMap(shift ->
                companyService.ifExists(shift.getCompanyId()).flatMap(exists -> (exists)
                        ? Mono.just(shift)
                        : Mono.error(new NotFoundException("Can't create Shift. Company with ID [" + shift.getCompanyId() + "] not found in DB")))
        ).flatMap(shift ->
                userService.existsById(shift.getUserId()).flatMap(exists -> (exists)
                        ? repository.save(shift)
                        : Mono.error(new NotFoundException("Can't create Shift. User with ID [" + shift.getUserId() + "] not found in DB"))

        ));
    }

    public Mono<Shift> update(Mono<Shift> shiftMono, String idFromDb) {
        return shiftMono.flatMap(shift -> repository.findById(idFromDb)
                        .flatMap(shiftFromDb -> {
                            if(shiftFromDb == null) {
                                return Mono.error(new NotFoundException("Can't update Shift. Shift with ID [" + idFromDb + "] not found in DB"));
                            }
                            BeanUtils.copyProperties(shift, shiftFromDb, "id");
                            return Mono.just(shiftFromDb);
                        }))
                .doOnNext(shift -> companyService.ifExists(shift.getCompanyId()).flatMap(exists -> (exists)
                        ? Mono.just(shift)
                        : Mono.error(new NotFoundException("Can't update Shift. Company with ID [" + shift.getCompanyId() + "] not found in DB"))))
                .doOnNext(shift -> userService.existsById(shift.getUserId()).flatMap(exist -> (exist)
                        ? repository.save(shift)
                        : Mono.error(new NotFoundException("Can't update Shift. User with ID [" + shift.getUserId() + "] not found in DB"))));
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
