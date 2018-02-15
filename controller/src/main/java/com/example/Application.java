package com.example;

import com.example.controller.UserController;
import com.example.repository.UserRepository;
import com.example.ws.UserListWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableWebFlux
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addInitializers(new ProgramaticBeanDefinitionInitializer());
        application.run(args);
    }

    public static class ProgramaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        public void initialize(GenericApplicationContext applicationContext) {
            applicationContext.registerBean(UserController.class,
                    () -> new UserController(applicationContext.getBean(UserRepository.class)));
            applicationContext.registerBean("userRouter", RouterFunction.class, () -> applicationContext.getBean(UserController.class).routerFunction);
            applicationContext.registerBean("resourcesRouter", RouterFunction.class, () -> RouterFunctions.resources("/**", new ClassPathResource("public/")));

            applicationContext.registerBean(UserListWebSocketHandler.class, () ->
                    new UserListWebSocketHandler(applicationContext.getBean(UserRepository.class), applicationContext.getBean(ObjectMapper.class)));

            applicationContext.registerBean(HandlerAdapter.class, () -> new WebSocketHandlerAdapter());

            applicationContext.registerBean(HandlerMapping.class, () -> {
                Map<String, WebSocketHandler> map = new HashMap<>();
                map.put("/userList", applicationContext.getBean(UserListWebSocketHandler.class));

                SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
                mapping.setUrlMap(map);
                return mapping;
            });

        }
    }
}
