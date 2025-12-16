package com.own.domain.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.domain.like.service.LikeService;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

	@Autowired
	private LikeService likeService;

	// 게시물 좋아요
	@PostMapping
	public ResponseEntity<Void> likePost(@PathVariable int postId, @RequestParam int userId) {

		likeService.likePost(postId, userId);
		return ResponseEntity.status(HttpStatus.CREATED).build(); // 201
	}

	// 게시물 좋아요 취소
	@DeleteMapping
	public ResponseEntity<Void> removeLike(@PathVariable int postId, @RequestParam int userId) {

		likeService.removeLike(postId, userId);
		return ResponseEntity.noContent().build(); // 204
	}

	// 특정 포스트의 좋아요 개수
	@GetMapping("/count")
	public ResponseEntity<Integer> getLikes(@PathVariable int postId) {
		int likes = likeService.getLikes(postId);
		return ResponseEntity.ok(likes); // 200
	}

	// 특정 유저가 특정 게시물에 좋아요 했는지 여부
	@GetMapping("/exists")
	public ResponseEntity<Boolean> isLiked(@PathVariable int postId, @RequestParam int userId) {
		boolean isLiked = likeService.isLiked(postId, userId);
		return ResponseEntity.ok(isLiked); // 200
	}
}
