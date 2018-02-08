# Spring5

The project is split into two modules: controller and repository

##Controller module contains

- UserController class with router functions,
- Client class
- three different cases of server classes (they all starts on the same port so they cant be started simultaneously):
    - Application - starts as SpringBoot Application
    - TomcatServer - starts using Tomcat server
    - NettyServer - starts using Netty server

##Repository module contains

- User POJO object
- UserRepository class.


