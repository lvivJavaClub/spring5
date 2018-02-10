package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DummyUserRepository implements UserRepository {
    private List<User> users = new ArrayList<>();

    public Flux<User> saveAll(Mono<User> userMono) {
        userMono.doOnNext(user -> {
            System.out.println("Add user " + user.getName());
            users.add(user);
            System.out.println("Users " + users);
        }).thenEmpty(Mono.empty());

        return Flux.fromIterable(users);
    }

    public Mono<Void> deleteAll(Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            System.out.println("Remove user " + user.getName());
            users.remove(user);
            System.out.println("Users " + users);
        }).thenEmpty(Mono.empty());
    }

    public Flux<User> findAll() {
        return Flux.fromIterable(users);
    }

    public Mono<User> findById(String id) {
        return Mono.justOrEmpty(users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null));
    }
}
