package com.fitness.userservice.services;

import com.fitness.userservice.dtos.requestDtos.RegisterRequest;
import com.fitness.userservice.dtos.responseDtos.UserResponse;
import org.jspecify.annotations.Nullable;

public interface UserService {

     UserResponse getUserProfile(Long userId);

    UserResponse register(RegisterRequest request);

     Boolean validateUser(Long userId);
}
