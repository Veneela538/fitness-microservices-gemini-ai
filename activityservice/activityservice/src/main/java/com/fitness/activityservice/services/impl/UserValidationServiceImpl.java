package com.fitness.activityservice.services.impl;

import com.fitness.activityservice.services.UserValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationServiceImpl implements UserValidationService {
    private final WebClient userServiceWebClient;
    public boolean validateUser(Long userId){
        try{
            log.info("Calling User Validation Api for userId: "+userId);
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        }catch (WebClientResponseException e){
            if(e.getStatusCode().value() == HttpStatus.SC_NOT_FOUND){
                throw new RuntimeException("User not found: "+userId);
            }
            else if(e.getStatusCode().value() == HttpStatus.SC_BAD_REQUEST){
                throw new RuntimeException("Invalid Request: "+userId);
            }
        }
        return false;
    }

}
