package com.example.server;

import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

import com.example.controller.UserController;
import com.example.repository.DummyUserRepository;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;


public class NettyServer {
    public static void main(String[] args) {
        UserController userController = new UserController(new DummyUserRepository());
        start(userController.routerFunction);
    }

    private static void start(RouterFunction<ServerResponse> routerFunction) {
        HttpHandler httpHandler = toHttpHandler(routerFunction);
        HttpServer.create("0.0.0.0")
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .doOnNext(foo -> System.out.println("Server listening on " + foo.address()))
                .block()
                .onClose()
                .block();
    }
}
