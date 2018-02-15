package com.example.model;

import java.util.UUID;

public class User {
    private String id;
    private String name;

    public User() {
        // DO NOT DO THIS ON PROD!!! IT IS BLOCKING OPERATION!!!
        id = UUID.randomUUID().toString();
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "{ id: " + id + ", name: " + name + "}";
    }
}
