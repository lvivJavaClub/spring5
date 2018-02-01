package com.example.controller;

import java.util.List;

import com.example.repository.User;
import com.example.repository.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public @ResponseBody int createUser(@RequestBody User user) {
        userRepository.add(user);
        return user.getId();
    }

    @GetMapping("/user/{id}")
    public @ResponseBody
    User getUser(@PathVariable Integer id) {
        return userRepository.getUser(id);
    }

    @GetMapping("/users")
    public @ResponseBody List<User> getUsers() {
        return userRepository.getUsers();
    }

    @DeleteMapping("/user")
    public @ResponseBody void deleteUser(@RequestBody User user) {
        userRepository.delete(user);
    }
}
