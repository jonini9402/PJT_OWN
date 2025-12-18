package com.own.domain.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.own.domain.post.dto.request.PostCreateRequest;
import com.own.domain.post.dto.request.PostSearchRequest;
import com.own.domain.post.dto.request.PostUpdateRequest;
import com.own.domain.post.dto.response.PostResponse;

public interface PostDao {
	
	//포스트 작성
	public void insertPost(PostCreateRequest request);
	
	//포스트 작성 시 감정 타입, 운동타입 추가
	public void insertPostEmotionType(@Param("postId") int postId, @Param("emotionTypeId") int emotionTypeId);
	public void insertPostWorkoutType(@Param("postId") int postId, @Param("workoutTypeId") int workoutTypeId);
	
	//포스트 수정 시 감정 타입 삭제
	public void deletePostEmotionTypes(int postId);
	
	//포스트 작성 후 생성되는 응답 객체 조회 메서드
	public PostResponse findPostResponseById(int postId);
	
	//전체 포스트 리스트 조회
	public List<PostResponse> findAll(PostSearchRequest request);
	
	//특정 유저의 게시글 리스트 조회
	public List<PostResponse> findByUserId(PostSearchRequest request);
	
	//유저별 좋아요한 게시물 조회
	public List<PostResponse> findLikedPosts(PostSearchRequest request);
	
	//유저별 북마크한 게시물 조회
	public List<PostResponse> findBookmarkedPosts(PostSearchRequest request);
	
	//포스트 수정
	public int updatePost(int postId, PostUpdateRequest request);
	
	//특정 포스트 삭제
	public int deletePost(int postId);
	
	
}
