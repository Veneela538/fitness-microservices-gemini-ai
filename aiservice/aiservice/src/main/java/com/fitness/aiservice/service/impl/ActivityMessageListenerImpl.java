package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.entities.Activity;
import com.fitness.aiservice.entities.Recommendation;
import com.fitness.aiservice.repositories.RecommendationRepository;
import com.fitness.aiservice.service.ActivityAIService;
import com.fitness.aiservice.service.ActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListenerImpl implements ActivityMessageListener {

    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        log.info("Received activity for processing: {}", activity.getId());
        log.info("Generated Recommendation: {}", aiService.generateRecommendation(activity));
        Recommendation recommendation = aiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }
}
