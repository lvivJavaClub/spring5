package com.example.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import com.example.repository.User;
import com.example.repository.UserRepo;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserHandler {

    private final UserRepo userRepository;

    public UserHandler(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userRepository.add(user));
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        int userId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> userMono = userRepository.getUser(userId);
        return userMono
                .flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(user)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getUsers(ServerRequest request) {
        Flux<User> userFlux = userRepository.getUsers();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userFlux, User.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userRepository.delete(user));
    }
}
