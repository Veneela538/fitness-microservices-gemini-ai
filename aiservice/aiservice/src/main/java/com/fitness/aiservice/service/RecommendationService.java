package com.fitness.aiservice.service;

import com.fitness.aiservice.entities.Recommendation;

import java.util.List;

public interface RecommendationService { 
    List<Recommendation> getUserRecommendation(Long userId);

    Recommendation getactivityRecommendation(String activityId);
}
