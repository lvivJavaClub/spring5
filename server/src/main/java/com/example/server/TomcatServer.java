package com.example.server;

import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

import com.example.controller.UserController;
import com.example.repository.UserRepository;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public class TomcatServer {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;

//    public static void main(String[] args) throws Exception {
//        UserController userController = new UserController(new UserRepository());
//        start(userController.routerFunction);
//    }

    private static void start(RouterFunction<ServerResponse> routerFunction) throws Exception {
        HttpHandler httpHandler = toHttpHandler(routerFunction);

        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setHostname(HOST);
        tomcatServer.setPort(PORT);
        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMapping("/", "httpHandlerServlet");
        tomcatServer.start();

        System.out.println("Press ENTER to exit.");
        System.in.read();
    }
}
