package com.example;

import java.net.URI;
import java.util.List;

import com.example.repository.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Client {

    private ExchangeFunction exchange = ExchangeFunctions.create(new ReactorClientHttpConnector());


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.createUser();
        client.printAllUsers();
    }

    public void printAllUsers() {
        URI uri = URI.create(String.format("http://%s:%d/users", Application.HOST, Application.PORT));
        ClientRequest request = ClientRequest.method(HttpMethod.GET, uri)
                .header("accept","application/json")
                .build();

        Flux<User> people = exchange.exchange(request)
                .flatMapMany(response -> response.bodyToFlux(User.class));

        Mono<List<User>> peopleList = people.collectList();
        System.out.println("RESPONSE: " + peopleList.block());
    }

    public void createUser() {
        URI uri = URI.create(String.format("http://%s:%d/user", Application.HOST, Application.PORT));
        User jack = new User("Jack Doe");

        ClientRequest request = ClientRequest.method(HttpMethod.POST, uri)
                .body(BodyInserters.fromObject(jack)).build();

        Mono<ClientResponse> response = exchange.exchange(request);

        System.out.println("RESPONSE: " + response.block().statusCode());
    }
}
