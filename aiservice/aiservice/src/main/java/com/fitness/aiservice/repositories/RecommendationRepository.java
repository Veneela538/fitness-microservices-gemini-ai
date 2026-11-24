package com.fitness.aiservice.repositories;

import com.fitness.aiservice.entities.Recommendation;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    List<Recommendation> findByUserId(Long userId);

    Optional<Recommendation> findByActivityId(String activityId);
}
