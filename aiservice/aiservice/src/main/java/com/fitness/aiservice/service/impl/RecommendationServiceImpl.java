package com.fitness.aiservice.service.impl;

import com.fitness.aiservice.entities.Recommendation;
import com.fitness.aiservice.repositories.RecommendationRepository;
import com.fitness.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final RecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> getUserRecommendation(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public Recommendation getactivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId).orElseThrow(()-> new RuntimeException("Recommendation not found for activityId "+activityId));
    }
}
