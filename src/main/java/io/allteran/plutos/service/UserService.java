package io.allteran.plutos.service;

import io.allteran.plutos.domain.User;
import io.allteran.plutos.exception.NotFoundException;
import io.allteran.plutos.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.dnd.MouseDragGestureRecognizer;

@Service
public class UserService implements ReactiveUserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
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

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findByEmail(username)
                .cast(UserDetails.class);
    }
}
