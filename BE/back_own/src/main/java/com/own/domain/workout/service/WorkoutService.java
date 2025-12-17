package com.own.domain.workout.service;

import java.util.List;

import com.own.domain.workout.dto.model.WorkoutType;
import com.own.domain.workout.dto.response.WorkoutTypeResponse;

public interface WorkoutService {
	
	List<WorkoutType> getAllWorkoutTypes();
	
	WorkoutTypeResponse getWorkoutTypesByPostId(int postId);
	
	// post_workout_type 에 포스트가 가진 workouttype 저장
	void saveWorkoutTypesForPost(int postId, int typeId);
	
}
