package io.allteran.plutos.service;

import io.allteran.plutos.domain.Salary;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.repo.SalaryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class SalaryService {
    private final SalaryRepository repository;
    private final CompanyService companyService;
    private final UserService userService;

    @Autowired
    public SalaryService(SalaryRepository repository, CompanyService companyService, UserService userService) {
        this.repository = repository;
        this.companyService = companyService;
        this.userService = userService;
    }

    public Flux<Salary> findAll() {
        return repository.findAll();
    }

    public Mono<Salary> findById(String id) {
        return repository.findById(id);
    }

    public Flux<Salary> findByUser(String userId) {
        return repository.findAllByUserId(userId);
    }

    public Flux<Salary> findByUserAndDate(String userId, LocalDateTime from, LocalDateTime to) {
        return repository.findAllByUserAndDate(userId, from, to);
    }

    public Flux<Salary> findByUserAndCompany(String userId, String companyId) {
        return repository.findAllByUserAndCompany(userId, companyId);
    }

    public Mono<Salary> create(Mono<Salary> salaryMono) {
        return salaryMono.flatMap(salary ->
                companyService.ifExists(salary.getCompanyId()).flatMap(exists -> (exists)
                        ? Mono.just(salary)
                        : Mono.error(new NotFoundException("Can't create Salary. Company with ID [" + salary.getCompanyId() + "] not found in DB")))
        ).doOnNext(salary ->
                userService.existsById(salary.getUserId()).flatMap(exists -> (exists)
                        ? repository.save(salary)
                        : Mono.error(new NotFoundException("Can't create Salary. User with ID [" + salary.getUserId() + "] not found in DB"))));
    }

    public Mono<Salary> update(Mono<Salary> salaryMono, String idFromDb) {
        return salaryMono.flatMap(salary -> repository.findById(idFromDb)
                        .flatMap(salaryFromDb -> {
                            if(salaryFromDb == null) {
                                return Mono.error(new NotFoundException("Can't update Salary. Salary with ID [" + idFromDb + "] not found in DB"));
                            }
                            BeanUtils.copyProperties(salary, salaryFromDb, "id");
                            return Mono.just(salaryFromDb);
                        }))
                .doOnNext(salary -> companyService.ifExists(salary.getCompanyId()).flatMap(exists -> (exists)
                        ? Mono.just(salary)
                        : Mono.error(new NotFoundException("Can't update Salary. Company with ID [" + salary.getCompanyId() + "] not found in DB"))))
                .doOnNext(salary -> userService.existsById(salary.getUserId()).flatMap(exist -> (exist)
                        ? repository.save(salary)
                        : Mono.error(new NotFoundException("Can't update Salary. User with ID [" + salary.getUserId() + "] not found in DB"))));
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
