package com.own.domain.bookmark.dao;

public interface BookmarkDao {
	// 북마크 하기
	void insertBookmark(int userId, int postId);

	// 북마크 취소
	int deleteBookmark(int userId, int postId);

	// 특정 포스트의 북마크 개수 세기
	int countBookmarkByPostId(int postId);

	// 특정 유저가 특정 게시물에 북마크했는지 체크
	Integer existsBookmark(int userId, int postId);

}
