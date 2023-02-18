package io.allteran.plutos.service;

import io.allteran.plutos.domain.User;
import io.allteran.plutos.exception.DuplicateException;
import io.allteran.plutos.exception.FieldException;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.repo.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements ReactiveUserDetailsService {
    private final UserRepository repository;
    private final CountryService countryService;
//    private final SalaryService salaryService;
    private final PasswordEncoder passwordEncoder;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    public UserService(UserRepository repository, CountryService countryService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.countryService = countryService;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> create(Mono<User> userMono) {
        return userMono
                .flatMap(user -> {
                    if(user.getPassword().equals(user.getPasswordConfirm())) {
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        user.setPasswordConfirm("");
                    } else {
                        user.setPassword("");
                        user.setPasswordConfirm("");
                        return Mono.error(new FieldException("Passwords don't match"));
                    }
                    if(!emailValidation(user.getEmail())) {
                        return Mono.error(new FieldException("Email [" + user.getEmail() + "] doesn't match the requirements. Notice, that email should match email pattern"));
                    }

                    return countryService.ifExist(user.getCountryId())
                            .flatMap(exist -> (exist) ? Mono.just(user) : Mono.error(new NotFoundException("Can't save user: Country with ID [" + user.getCountryId() + "] not found in database")));
                })
                //TODO IN THE FUTURE: validate privilege check when create user
                .flatMap(user -> repository.existsUserByEmail(user.getEmail())
                        .flatMap(exist -> (exist) ? Mono.error(new FieldException("User with EMAIL [" + user.getEmail() + "] already exist in database")) : repository.save(user)));
    }

    public Mono<User> update(String idFromDb, Mono<User> userMono) {
        return userMono
                .flatMap(user -> repository.findById(idFromDb).flatMap(userFromDb -> {
                    if (userFromDb == null) {
                        return Mono.error(new NotFoundException("Can't update User. User with ID [" + idFromDb + "] not found in database"));
                    }
                    if(!user.getNewPassword().equals(user.getPasswordConfirm())) {
                        return Mono.error(new FieldException("Can't update User. Passwords don't match"));
                    }
                    String originalEmail = userFromDb.getEmail();

                    BeanUtils.copyProperties(user, userFromDb, "id", "password", "newPassword", "password", "passwordConfirm");
                    userFromDb.setPassword(passwordEncoder.encode(user.getNewPassword()));
                    userFromDb.setPasswordConfirm("");
                    userFromDb.setNewPassword("");
                    return repository.existsUserByEmail(userFromDb.getEmail())
                            .flatMap(exist ->  {
                                if(!originalEmail.equals(userFromDb.getEmail())) {
                                    if(exist) {
                                        return Mono.error(new DuplicateException("User with EMAIL [" + user.getEmail() + "] already exist in database"));
                                    }
                                }
                                return repository.save(userFromDb);
                            });
                }));
    }

    public Mono<User> updateRaw(String idFromDb, Mono<User> userMono) {
        return userMono
                .flatMap(user -> repository.findById(idFromDb).flatMap(userFromDb -> {
                    if (userFromDb == null) {
                        return Mono.error(new NotFoundException("Can't update User. User with ID [" + idFromDb + "] not found in database"));
                    }
                    String originalEmail = userFromDb.getEmail();

                    BeanUtils.copyProperties(user, userFromDb, "id", "password", "newPassword", "password", "passwordConfirm");
                    userFromDb.setPassword(passwordEncoder.encode(user.getNewPassword()));
                    userFromDb.setPasswordConfirm("");
                    userFromDb.setNewPassword("");
                    return repository.existsUserByEmail(userFromDb.getEmail())
                            .flatMap(exist ->  {
                                if(!originalEmail.equals(userFromDb.getEmail())) {
                                    if(exist) {
                                        return Mono.error(new FieldException("User with EMAIL [" + user.getEmail() + "] already exist in database"));
                                    }
                                }
                                return repository.save(userFromDb);
                            });

                }));
    }

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<User> findById(String id) {
        return repository.findById(id);
    }

    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Flux<User> findByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public Flux<User> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public Flux<User> findByOccupation(String occupation) {
        return repository.findByOccupation(occupation);
    }

    public Flux<User> findByCountry(String countryId) {
        return repository.findByCountry(countryId);
    }

    //TBH this shit just using same query as findByEmail, but for some reason I can't cast from User to UserDetails OR BACK

    public Mono<User> createAdmin(Mono<User> user) {
        return user.flatMap(repository::save);
    }

    private boolean emailValidation(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findByEmail(username)
                .cast(UserDetails.class);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
//                .doOnNext(unused -> salaryService.findByUser(id)
//                        .doOnNext(salaryDTO -> salaryService.delete(salaryDTO.getId())));
    }

    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }
}
