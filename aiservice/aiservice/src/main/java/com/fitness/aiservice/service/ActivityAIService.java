package com.fitness.aiservice.service;

import com.fitness.aiservice.entities.Activity;
import com.fitness.aiservice.entities.Recommendation;

public interface ActivityAIService {
    public Recommendation generateRecommendation(Activity activity);
}
