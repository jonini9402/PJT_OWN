package com.own.domain.workout.dto.request;

public class PostWorkoutRequest {
	
	private int postId;
	private int workoutTypeId;
	
	public PostWorkoutRequest() {
		
	}
	
	public PostWorkoutRequest(int postId, int workoutTypeId) {
		this.postId = postId;
		this.workoutTypeId = workoutTypeId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getWorkoutTypeId() {
		return workoutTypeId;
	}

	public void setWorkoutTypeId(int workoutTypeId) {
		this.workoutTypeId = workoutTypeId;
	}
	
	
}
