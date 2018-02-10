package com.example.repository;

import com.example.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Flux<User> saveAll(Mono<User> userMono);

    Mono<User> findById(String id);

    Flux<User> findAll();

    Mono<Void> deleteAll(Mono<User> userMono);
}
