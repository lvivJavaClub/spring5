package com.example.repository;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public void delete(User user) {
        users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }
}
