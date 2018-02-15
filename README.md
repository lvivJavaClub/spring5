# Spring5 Functional Web Framework Sample.

The project is a sample application that uses the functional web framework introduced in Spring 5.

## Build

Use maven to build this project:

```bash
mvn clean instal
```  

## The structure of the project

### Controller module contains

- `UserController` class with router functions.
- `Application` - starts as SpringBoot Application with Netty server.

### Repository module contains

- `User` POJO object
- `UserRepository` Mongo reactive crud repository class.

### Server module contains

- `TomcatServer`. Contains a `main` method to start the server using Tomcat server.
- `NettyServer`. Contains a `main` method to start the server using Reactor Netty server.
- `Client`. Contains a `main` method to start the client.

## Running

#### As spring boot application
 - Run the `Application` class
 
#### Reactor Netty server
 - Run the `NettyServer` class

#### Tomcat server
 - Run the `TomcatServer` class

#### Client
 - Run the `Client` class


## Useing

### Sample curl commands

Instead of running the client, here are some sample `curl` commands that access resources exposed
by this sample:

#### Retrieve list of users

```sh
curl -v 'http://localhost:8080/users'
```

#### Retrieve user by id 

```sh
curl -v 'http://localhost:8080/user/{id}'
```

#### Create new user

```sh
curl -d '{"name":"Jack Doe"}' -H 'Content-Type: application/json' -v 'http://localhost:8080/user'
```
