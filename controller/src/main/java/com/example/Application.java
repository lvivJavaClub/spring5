package com.example;

import com.example.controller.UserController;
import com.example.repository.ReactiveUserRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


@SpringBootApplication
@EnableReactiveMongoRepositories
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
@EnableWebFlux
public class Application extends AbstractReactiveMongoConfiguration{

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addInitializers(new ProgramaticBeanDefinitionInitializer());
        application.run(args);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(UserController userController) {
        return userController.routerFunction;
    }

    @Bean
    public LoggingEventListener mongoEventListener() {
        return new LoggingEventListener();
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(String.format("mongodb://localhost:%d", 27017));
    }

    @Override
    protected String getDatabaseName() {
        return "reactive";
    }

    public static class ProgramaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        public void initialize(GenericApplicationContext applicationContext) {
            applicationContext.registerBean(UserController.class,
                    () -> new UserController(applicationContext.getBean(ReactiveUserRepository.class)));
        }
    }
}
