package com.own.domain.post.dto.model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
	private int postId;
	private int userId;
	private String caption;
	private Integer musicId;
	private LocalDateTime createdAt;
	private Integer workoutTag;  
	private List<Integer> emotionTypeIds; 
	
	public Post() {
	}
	
	public Post(int postId, int userId, String caption, Integer musicId, LocalDateTime createdAt, Integer workoutTag,
			List<Integer> emotionTypeIds) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.caption = caption;
		this.musicId = musicId;
		this.createdAt = createdAt;
		this.workoutTag = workoutTag;
		this.emotionTypeIds = emotionTypeIds;
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getCaption() {
		return caption;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}


	public Integer getMusicId() {
		return musicId;
	}


	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public Integer getWorkoutTag() {
		return workoutTag;
	}


	public void setWorkoutTag(Integer workoutTag) {
		this.workoutTag = workoutTag;
	}


	public List<Integer> getEmotionTypeIds() {
		return emotionTypeIds;
	}


	public void setEmotionTypeIds(List<Integer> emotionTypeIds) {
		this.emotionTypeIds = emotionTypeIds;
	}


	@Override
	public String toString() {
		return "Post [postId=" + postId + ", userId=" + userId + ", caption=" + caption + ", musicId=" + musicId
				+ ", createdAt=" + createdAt + ", workoutTag=" + workoutTag + ", emotionTypeIds=" + emotionTypeIds
				+ "]";
	}
	
	
}
