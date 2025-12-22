package com.own.domain.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.domain.post.dto.request.PostCreateRequest;
import com.own.domain.post.dto.request.PostSearchRequest;
import com.own.domain.post.dto.request.PostUpdateRequest;
import com.own.domain.post.dto.response.MusicRankResponse;
import com.own.domain.post.dto.response.PostResponse;
import com.own.domain.post.service.PostService;
import com.own.domain.user.dto.model.User;
import com.own.global.exception.CustomException;
import com.own.global.exception.ErrorCode;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PostController {

	@Autowired
	private PostService postService;

	// 게시글 생성
	@PostMapping
	public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest request, HttpSession session) {
		//1. 세션에서 로그인 사용자 ID를 가져옴 (로그인 시 "loginUser"라는 이름으로 저장)
		Integer currentUserId = (Integer) session.getAttribute("loginUserId");
		
		if (currentUserId == null) {
	        // 2. 로그인되지 않은 경우의 예외 처리 (401 Unauthorized 등)
	        throw new CustomException(ErrorCode.USER_NOT_FOUND); 
	    }
		
		//테스트용
//		currentUserId = 1;
		
		//가져온 ID를 PostCreateRequest 객체에 설정
		request.setUserId(currentUserId);
		
		PostResponse response = postService.createPost(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// 피드 게시글 조회
	@GetMapping
	public ResponseEntity<List<PostResponse>> getFeedPosts(PostSearchRequest request) {
		return ResponseEntity.ok(postService.getFeedPosts(request));
	}

	// 특정 유저의 게시글 조회
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable int userId, PostSearchRequest request) {
		request.setUserId(userId);

		return ResponseEntity.ok(postService.getUserPosts(request));
	}

	// 특정 유저가 좋아요한 게시글 조회
	@GetMapping("/likes/{userId}")
	public ResponseEntity<List<PostResponse>> getLikedPosts(@RequestParam int userId,@ModelAttribute PostSearchRequest request) {
		request.setUserId(userId);
		return ResponseEntity.ok(postService.getLikedPosts(request));
	}

	// 특정 유저가 북마크한 게시글 조회
	@GetMapping("/bookmarks/{userId}")
	public ResponseEntity<List<PostResponse>> getBookmarkedPosts(@RequestParam int userId,@ModelAttribute PostSearchRequest request) {
		request.setUserId(userId);
		return ResponseEntity.ok(postService.getBookmarkedPosts(request));
	}

	// 게시글 수정
	@PutMapping("/{postId}")
	public ResponseEntity<Void> updatePost(@PathVariable int postId, @RequestBody PostUpdateRequest request) {
		postService.updatePost(postId, request);

		return ResponseEntity.noContent().build();  // 204

	}

	// 게시글삭제
	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable int postId) {
		postService.deletePost(postId);

		return ResponseEntity.noContent().build();  // 204
	}
	
	//랭킹조회
	@GetMapping("/rank")
	public ResponseEntity<List<MusicRankResponse>> getMusicRank() {
		List<MusicRankResponse> musicRank = postService.getMusicRank();
		
		return ResponseEntity.ok(musicRank);
	}
	
	//이번주 운동횟수 조회
	@GetMapping("/week-count")
	public ResponseEntity<Integer> getWeeklyCount(HttpSession session) {
		
		Integer userId = (Integer) session.getAttribute("loginUserId");
		
		if(userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		int count = postService.getWeeklyWorkoutCount(userId);
		return ResponseEntity.ok(count);
	}
}
