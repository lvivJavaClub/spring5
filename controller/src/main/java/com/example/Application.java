package com.example;

import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

import com.example.controller.UserController;
import com.example.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.EnableWebFlux;
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
  RouterFunction<ServerResponse> routerFunction(UserController userController) {
    return RouterFunctions
            .route(path("/users"), userController::getUsers)
            .andRoute(path("/user").and(method(HttpMethod.POST)), userController::createUser)
            .andRoute(path("/user").and(method(HttpMethod.DELETE)), userController::deleteUser)
            .andRoute(path("/user/{id}"), userController::getUser);
  }

    public static class ProgramaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        public void initialize(GenericApplicationContext applicationContext) {
            //applicationContext.registerBean(UserRepository.class, UserRepository::new);
            applicationContext.registerBean(UserController.class,
                    () -> new UserController(applicationContext.getBean(UserRepository.class)));
        }
    }
}
