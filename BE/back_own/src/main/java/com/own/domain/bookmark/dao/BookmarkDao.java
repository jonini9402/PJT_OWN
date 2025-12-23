package com.own.domain.bookmark.dao;

import org.apache.ibatis.annotations.Param;

public interface BookmarkDao {
	// 북마크 하기
	void insertBookmark(@Param("userId") int userId, @Param("postId") int postId);

	// 북마크 취소
	int deleteBookmark(@Param("userId") int userId, @Param("postId") int postId);

	// 특정 포스트의 북마크 개수 세기
	int countBookmarkByPostId(int postId);

	// 특정 유저가 특정 게시물에 북마크했는지 체크
	Integer existsBookmark(@Param("userId") int userId, @Param("postId") int postId);

}
