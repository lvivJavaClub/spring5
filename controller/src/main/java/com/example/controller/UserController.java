package com.example.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserController {

    public RouterFunction<ServerResponse> routerFunction = route(GET("/user/{id}").and(accept(APPLICATION_JSON)), this::getUser)
            .andRoute(GET("/users").and(accept(APPLICATION_JSON)), this::getUsers)
            .andRoute(POST("/user").and(contentType(APPLICATION_JSON)), this::createUser)
            .andRoute(DELETE("/user").and(contentType(APPLICATION_JSON)), this::deleteUser);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userRepository.saveAll(user));
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> userMono = userRepository.findById(userId);
        return userMono
                .flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(user)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getUsers(ServerRequest request) {
        Flux<User> userFlux = userRepository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userFlux, User.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userRepository.deleteAll(user));
    }
}
