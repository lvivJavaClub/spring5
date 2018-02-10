package com.example.repository;

import com.example.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveUserRepository extends UserRepository, ReactiveCrudRepository<User, String> {
}
