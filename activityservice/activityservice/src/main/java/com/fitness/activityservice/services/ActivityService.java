package com.fitness.activityservice.services;

import com.fitness.activityservice.dto.requestDtos.ActivityRequest;
import com.fitness.activityservice.dto.responseDtos.ActivityResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest request);

    List<ActivityResponse> getUserActivities(Long userId);

    ActivityResponse getActivity(String activityId);
}
