package com.own.domain.workout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.domain.workout.dto.model.WorkoutType;
import com.own.domain.workout.dto.request.PostWorkoutRequest;
import com.own.domain.workout.dto.response.WorkoutTypeResponse;
import com.own.domain.workout.service.WorkoutService;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
	
	@Autowired
	private WorkoutService workoutService;
	
	@GetMapping
	public List<WorkoutType> getAllWorkoutTypes() {
		return workoutService.getAllWorkoutTypes();
	}
	
	// postId로 특정 포스트의 태그 조회
	@GetMapping("/post/{postId}")
	public WorkoutTypeResponse getWorkoutTypesByPost(@PathVariable int postId){
		
		return workoutService.getWorkoutTypesByPostId(postId);
	}
	
	// 포스트에 태그 저장 - post_workout_type 테이블
	@PostMapping("/post")
	public void savePostWorkout(@RequestBody PostWorkoutRequest post) {
		
		workoutService.saveWorkoutTypesForPost(post.getPostId(), post.getWorkoutTypeId());
		
	}
	
}
