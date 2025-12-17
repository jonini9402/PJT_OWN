package com.own.domain.workout.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.domain.workout.dao.WorkoutDao;
import com.own.domain.workout.dto.model.WorkoutType;
import com.own.domain.workout.dto.response.WorkoutTypeResponse;

@Service
public class WorkoutServiceImpl implements WorkoutService {

	@Autowired
	private WorkoutDao workoutDao;
	
	@Override
	public WorkoutTypeResponse getWorkoutTypesByPostId(int postId) {
		
		return workoutDao.selectWorkoutTypesByPostId(postId);
	}

	@Override
	public void saveWorkoutTypesForPost(int postId, int typeId) {
		
		
			workoutDao.insertPostWorkoutType(postId, typeId);
		
		
	}

	@Override
	public List<WorkoutType> getAllWorkoutTypes() {
		
		return workoutDao.selectAllWorkoutTypes();
	}

}
