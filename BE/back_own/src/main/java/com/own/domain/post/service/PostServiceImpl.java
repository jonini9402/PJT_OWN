package com.own.domain.post.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.domain.emotion.service.EmotionService;
import com.own.domain.post.dao.PostDao;
import com.own.domain.post.dto.request.PostCreateRequest;
import com.own.domain.post.dto.request.PostSearchRequest;
import com.own.domain.post.dto.request.PostUpdateRequest;
import com.own.domain.post.dto.response.PostResponse;
import com.own.domain.workout.service.WorkoutService;
import com.own.global.exception.CustomException;
import com.own.global.exception.ErrorCode;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private EmotionService emotionService;

	@Override
	@Transactional
	public PostResponse createPost(PostCreateRequest request) {
		// 1. 게시글 저장, postId 생성
		postDao.insertPost(request);
		Integer postId = request.getPostId();

		if (postId == null) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

		workoutService.saveWorkoutTypesForPost(postId, request.getWorkoutTag());

		if (request.getEmotionTags() != null && !request.getEmotionTags().isEmpty()) {
			emotionService.saveEmotionTypesForPost(postId, request.getEmotionTags());
		}
		//Post ID를 사용하여 최종적으로 클라이언트에게 보낼 PostResponse 객체를 조회/생성
		PostResponse response = postDao.findPostResponseById(postId);

		//PostResponse 객체 반환
		return response;
	}

	@Override
	public List<PostResponse> getFeedPosts(PostSearchRequest request) {
		return postDao.findAll(request);

	}

	@Override
	public List<PostResponse> getUserPosts(PostSearchRequest request) {
		return postDao.findByUserId(request);
	}

	@Override
	public List<PostResponse> getLikedPosts(PostSearchRequest request) {
		return postDao.findLikedPosts(request);
	}

	@Override
	public List<PostResponse> getBookmarkedPosts(PostSearchRequest request) {
		return postDao.findBookmarkedPosts(request);
	}

	@Override
	@Transactional
	public int updatePost(int postId, PostUpdateRequest request) {
		int updated = postDao.updatePost(postId, request);

		if (updated == 0) {
			throw new CustomException(ErrorCode.POST_NOT_FOUND);
		}
		return updated; // 성공 시 1
	}

	@Override
	@Transactional
	public int deletePost(int postId) {
		int deleted = postDao.deletePost(postId);

		if (deleted == 0) {
			throw new CustomException(ErrorCode.POST_NOT_FOUND);

		}
		return deleted; // /성공 시 1

	}
}
