# Spring5 Functional Web Framework Sample

The project is a sample application that uses the functional web framework introduced in Spring 5.
It consists of the following types:

The project is split into two modules: controller and repository

##Repository module contains

| Class                   | Description                                                             |
| ----------------------- | ----------------------------------------------------------------------- |
| `User`                  | POJO representing a user                                                |
| `UserRepository`        | Dummy repository for `User`                                             |


##Controller module contains

| Class                   | Description                                                              |
| ----------------------- | ------------------------------------------------------------------------ |
| `UserController`        | Web handler that exposes a `UserRepository`                              |
| `Application`           | Contains a `main` method to start the server as s SpringBoot Application |
| `TomcatServer`          | Contains a `main` method to start the server using Tomcat server         |
| `NettyServer`           | Contains a `main` method to start the server using Reactor Netty server  |
| `Client`                | Contains a `main` method to start the client                             |

These three different server classes are just for demo. They all starts on the same port so they cant be started simultaneously.

### Running the Spring Boot Application
 - Run the `Application` class

 It uses Netty under the hood


### Running the Reactor Netty server
 - Run the `NettyServer` class

### Running the Tomcat server
 - Run the `TomcatServer` class

### Running the Client
 - Build using maven
 - Run the `Client` class

### Sample curl commands

Instead of running the client, here are some sample `curl` commands that access resources exposed
by this sample:

```sh
curl -v 'http://localhost:8080/users'
curl -v 'http://localhost:8080/user/{id}'
curl -d '{"name":"Jack Doe"}' -H 'Content-Type: application/json' -v 'http://localhost:8080/user'
```


