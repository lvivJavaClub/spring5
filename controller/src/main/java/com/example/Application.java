package com.example;

import reactor.core.publisher.Mono;

import com.example.controller.UserController;
import com.example.repository.UserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@SpringBootApplication
@EnableWebFlux
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addInitializers(new ProgramaticBeanDefinitionInitializer());
        application.run(args);
    }

  @Bean
  RouterFunction<ServerResponse> helloRouterFunction() {
    return RouterFunctions.route(RequestPredicates.path("/"),
        serverRequest -> ServerResponse.ok().body(Mono.just("Hello World!"), String.class));
  }

    public static class ProgramaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        public void initialize(GenericApplicationContext applicationContext) {
            applicationContext.registerBean(UserRepository.class, UserRepository::new);
            applicationContext.registerBean(UserController.class,
                    () -> new UserController(applicationContext.getBean(UserRepository.class)));
        }
    }
}
