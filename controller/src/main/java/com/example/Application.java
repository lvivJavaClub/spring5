package com.example;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

import com.example.controller.UserHandler;
import com.example.repository.UserRepo;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

@SpringBootApplication
@EnableWebFlux
public class Application {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;


    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        application.addInitializers(new ProgramaticBeanDefinitionInitializer());
//        SpringApplication.run(Application.class, args);

        startTomcatServer();

//        startNettyServer();

        System.out.println("Press ENTER to exit.");
        System.in.read();



    }

    public static class ProgramaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

        public void initialize(GenericApplicationContext applicationContext) {
//            applicationContext.registerBean(UserRepository.class, UserRepository::new);
//            applicationContext.registerBean(UserController.class,
//                    () -> new UserController(applicationContext.getBean(UserRepository.class)));

            applicationContext.registerBean(UserRepo.class, UserRepo::new);
            applicationContext.registerBean(UserHandler.class,
                    () -> new UserHandler(applicationContext.getBean(UserRepo.class)));
        }
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(GET("/user/{id}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::getUsers)
                .andRoute(POST("/user").and(contentType(APPLICATION_JSON)), userHandler::createUser)
            .andRoute(DELETE("/user").and(contentType(APPLICATION_JSON)), userHandler::deleteUser);
    }

    private static RouterFunction<ServerResponse> routerFunction() {
        UserHandler userHandler = new UserHandler(new UserRepo());
        return route(GET("/user/{id}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::getUsers)
                .andRoute(POST("/user").and(contentType(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(DELETE("/user").and(contentType(APPLICATION_JSON)), userHandler::deleteUser);
    }

    private static void startNettyServer() {
        HttpHandler httpHandler = toHttpHandler(routerFunction());
        HttpServer.create("0.0.0.0")
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .doOnNext(foo -> System.out.println("Server listening on " + foo.address()))
                .block()
                .onClose()
                .block();
    }

    private static void startTomcatServer() throws LifecycleException {
        HttpHandler httpHandler = toHttpHandler(routerFunction());

        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setHostname(HOST);
        tomcatServer.setPort(PORT);
        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMapping("/", "httpHandlerServlet");
        tomcatServer.start();
    }
}
