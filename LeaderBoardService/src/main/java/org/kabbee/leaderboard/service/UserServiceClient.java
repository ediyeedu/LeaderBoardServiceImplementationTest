package org.kabbee.leaderboard.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kabbee.leaderboard.dto.UserDto;
import org.kabbee.leaderboard.model.User;
import org.kabbee.leaderboard.repository.CachedUserRepository;
import org.kabbee.leaderboard.service.serviceImp.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;
    private final CachedUserRepository cachedUserRepository;

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);

    @Value ( "${user.service.url}" )
    private String userUrl;
    public User getUserById(Long userId) {

        logger.info("Attempting to retrieve user with ID: {}", userId);

        // Check if the user is present in the cache
        Optional<User> cachedUserOptional = cachedUserRepository.findUserByUserId(userId);
        if (cachedUserOptional.isPresent()) {
            logger.info("User found in cache with ID: {}", userId);
            return cachedUserOptional.get();
        }

        logger.info("User not found in cache, fetching from remote service with ID: {}", userId);

        // If not in cache, fetch from remote service
        UserDto userDto = webClient.get()
                .uri(userUrl + "/api/users/ext/{id}", userId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        logger.error("User not found with ID: {}", userId);
                        return Mono.error(new RuntimeException("User not found with ID: " + userId));
                    }
                    logger.error("Client error occurred: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Client error occurred: " + response.statusCode()));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    logger.error("Server error occurred: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Server error occurred: " + response.statusCode()));
                })
                .bodyToMono(UserDto.class)
                .block();

        // Convert UserDto to User and cache it
        if (userDto != null) {
            logger.info("User fetched successfully from remote service with ID: {}", userId);
            User newCachedUser = User.builder()
                    .userId(userId)
                    .firstname(userDto.getFirstname())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .build();
            cachedUserRepository.save(newCachedUser);
            logger.info("User saved to cache with ID: {}", userId);
            return newCachedUser;
        }

        logger.error("UserDto is null for ID: {}", userId);
        return null; // Or throw an appropriate exception
    }
}