package com.example.ws;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author Andriy Levchenko
 */
public class UserListWebSocketHandler implements WebSocketHandler {

    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    public UserListWebSocketHandler(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(userRepository.findAll()
                .map(this::serialize)
                .map(session::textMessage));
    }

    private String serialize(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error", e);
        }
    }
}
