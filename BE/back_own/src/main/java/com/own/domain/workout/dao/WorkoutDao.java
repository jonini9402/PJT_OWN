package com.own.domain.workout.dao;

import java.util.List;

import com.own.domain.workout.dto.model.WorkoutType;
import com.own.domain.workout.dto.response.WorkoutTypeResponse;

public interface WorkoutDao {
	
	// 전체 리스트 조회 (db 연결 체크용)
	List<WorkoutType> selectAllWorkoutTypes();
	
	// 특정 포스트의 운동 태그 조회
	WorkoutTypeResponse selectWorkoutTypesByPostId(int postId);
	
	// post_workout_type 테이블에 추가
	void insertPostWorkoutType(int postId, int typeId);
	
	
	
}
