package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class Application {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.addInitializers(new ProgramaticBeanDefinitionInitializer());
        application.run(args);
    }

    public static class ProgramaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        public void initialize(GenericApplicationContext applicationContext) {
//            applicationContext.registerBean(UserRepository.class, UserRepository::new);
//            applicationContext.registerBean(UserController.class,
//                    () -> new UserController(applicationContext.getBean(UserRepository.class)));

//            applicationContext.registerBean(UserRepo.class, UserRepo::new);
//            applicationContext.registerBean(UserHandler.class,
//                    () -> new UserHandler(applicationContext.getBean(UserRepo.class)));
        }
    }
}
