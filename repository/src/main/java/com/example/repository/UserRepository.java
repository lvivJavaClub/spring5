package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public Mono<Void> add(Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            System.out.println("Add user " + user.getName());
            users.add(user);
            System.out.println("Users " + users);
        }).thenEmpty(Mono.empty());
    }

    public Mono<Void> delete(Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            System.out.println("Remove user " + user.getName());
            users.remove(user);
            System.out.println("Users " + users);
        }).thenEmpty(Mono.empty());
    }

    public Flux<User> getUsers() {
        return Flux.fromIterable(users);
    }

    public Mono<User> getUser(Integer id) {
        return Mono.justOrEmpty(users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null));
    }
}
