package com.own.domain.like.dao;

public interface LikeDao {
	//게시물 좋아요
	void insertLike(int userId, int postId);
	
	//게시물 좋아요 취소
	int deleteLike(int userId, int postId);
	
	//특정 포스트의 좋아요 개수
	int countLikeByPostId(int postId);
	
	//특정 유저가 특정 게시물에 좋아요했는지 여부
	boolean existsLike(int userId, int postId);
	
}
