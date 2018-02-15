package com.example.repository;

import reactor.core.publisher.Mono;

import com.example.model.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

  Mono<Void> deleteAll(Mono<User> userMono);
  Mono<Void> saveAll(Mono<User> userMono);

}
