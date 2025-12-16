package com.own.domain.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.domain.like.dao.LikeDao;
import com.own.global.exception.CustomException;
import com.own.global.exception.ErrorCode;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired LikeDao likeDao; 
	
		//게시물 좋아요 
		public void likePost(int postId,int userId) {
			//중복 방지 사전 체크
			if(isLiked(postId, userId)) {
				throw new CustomException(ErrorCode.ALREADY_LIKED);
			}
			//좋아요 등록
			likeDao.insertLike(postId,userId);
		}
		
		//게시물 좋아요 취소
		public void removeLike(int postId, int userId) {
			int deletedCount = likeDao.deleteLike(userId, postId);
			if(deletedCount == 0) {
				throw new CustomException(ErrorCode.LIKE_NOT_FOUND); //400
			}
			// deletedCount가 1인 경우 성공
		}
		
		//특정 포스트의 좋아요 개수 세기
		public int getLikes(int postId) {
			int bookmarks = likeDao.countLikeByPostId(postId);
			return bookmarks;
			
		}
		
		//특정 유저가 특정 게시물에 좋아요했는지 체크
		public boolean isLiked(int postId, int userId) {
			return likeDao.existsLike(postId,userId);
		}
}
