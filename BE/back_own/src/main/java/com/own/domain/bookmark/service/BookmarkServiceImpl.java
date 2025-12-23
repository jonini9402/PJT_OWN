package com.own.domain.bookmark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.domain.bookmark.dao.BookmarkDao;
import com.own.global.exception.CustomException;
import com.own.global.exception.ErrorCode;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	private BookmarkDao bookmarkDao;
	
	//게시물 북마크
	public void bookmarkPost(int postId, int userId) {
		//중복 방지 사전 체크
		if(isBookmarked(postId, userId)) {
			throw new CustomException(ErrorCode.ALREADY_BOOKMARKED);
		}
		//북마크 등록
		bookmarkDao.insertBookmark(userId,postId);
	}
	
	//게시물 북마크 취소
	public void removeBookmark(int postId, int userId) {
		int deletedCount = bookmarkDao.deleteBookmark(userId, postId);
		if(deletedCount == 0) {
			throw new CustomException(ErrorCode.BOOKMARK_NOT_FOUND); //400
		}
		// deletedCount가 1인 경우 성공
	}
	
	//특정 포스트의 북마크 개수 세기
	public int getBookmarks(int postId) {
		int bookmarks = bookmarkDao.countBookmarkByPostId(postId);
		return bookmarks;
	}
	
	//특정 유저가 특정 게시물에 북마크했는지 체크
	public boolean isBookmarked(int postId, int userId) {
		Integer result = bookmarkDao.existsBookmark(userId,postId);
		return result != null && result > 0;
	}

	
}
