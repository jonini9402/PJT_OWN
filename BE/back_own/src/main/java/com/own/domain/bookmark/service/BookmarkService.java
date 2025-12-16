package com.own.domain.bookmark.service;

public interface BookmarkService {
	
	//게시물 북마크
	void bookmarkPost(int postId,int userId);
	
	//게시물 북마크 취소
	void removeBookmark(int postId, int userId);
	
	//특정 포스트의 북마크 개수 세기
	int getBookmarks(int postId);
	
	//특정 유저가 특정 게시물에 북마크했는지 체크
	boolean isBookmarked(int postId, int userId);
	

}
