package com.fitness.activityservice.services.impl;

import com.fitness.activityservice.dto.requestDtos.ActivityRequest;
import com.fitness.activityservice.dto.responseDtos.ActivityResponse;
import com.fitness.activityservice.entities.Activity;
import com.fitness.activityservice.repositories.ActivityRepository;
import com.fitness.activityservice.services.ActivityService;
import com.fitness.activityservice.services.UserValidationService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Autowired
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if(!isValidUser){
            throw new RuntimeException("Invalid User: "+request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
        }catch(Exception e){
            log.error("Failed to publish activity to RabbitMq ",e);
        }
        return mapToResponse(savedActivity);

    }

    @Override
    public List<ActivityResponse> getUserActivities(Long userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        List<ActivityResponse> activityResponses = activities.stream().map(this::mapToResponse).collect(Collectors.toList());
        return activityResponses;
    }

    @Override
    public ActivityResponse getActivity(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(()->new RuntimeException("Activity not found with id "+activityId));
        return mapToResponse(activity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(savedActivity.getId());
        activityResponse.setUserId(savedActivity.getUserId());
        activityResponse.setType(savedActivity.getType());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setStartTime(savedActivity.getStartTime());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        activityResponse.setCreatedAt(savedActivity.getCreatedAt());
        activityResponse.setUpdatedAt(savedActivity.getUpdatedAt());
        return activityResponse;
    }
}
