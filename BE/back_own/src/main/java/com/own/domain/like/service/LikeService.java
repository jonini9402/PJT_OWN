package com.own.domain.like.service;

public interface LikeService {

	//게시물 좋아요 
	void likePost(int postId,int userId);
	
	//게시물 좋아요 취소
	void removeLike(int postId, int userId);
	
	//특정 포스트의 좋아요 개수 세기
	int getLikes(int postId);
	
	//특정 유저가 특정 게시물에 좋아요했는지 체크
	boolean isLiked(int postId, int userId);
}
