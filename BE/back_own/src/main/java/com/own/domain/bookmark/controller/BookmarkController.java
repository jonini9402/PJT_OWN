package com.own.domain.bookmark.controller;

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

import com.own.domain.bookmark.service.BookmarkService;

@RestController 
@RequestMapping("/api/posts/{postId}/bookmarks")
public class BookmarkController {
	
	@Autowired
	private BookmarkService bookmarkService;
	
	//게시물 북마크
	@PostMapping
	public ResponseEntity<Void> bookmarkPost(@PathVariable int postId, @RequestParam int userId){
		
		bookmarkService.bookmarkPost(postId, userId);
		return ResponseEntity.status(HttpStatus.CREATED).build(); //201 created
	}
	
	//게시물 북마크 취소
	@DeleteMapping
	public ResponseEntity<Void> removeBookmark(@PathVariable int postId, @RequestParam int userId){
		bookmarkService.removeBookmark(postId, userId);
		return ResponseEntity.noContent().build(); //삭제 성공 시 204 no Content 
	}
	
	//특정 포스트의 북마크 개수 세기
	@GetMapping("/count")
	public ResponseEntity<Integer> getBookmarks(@PathVariable int postId){
		int bookmarks = bookmarkService.getBookmarks(postId);
		
		return ResponseEntity.ok(bookmarks); // 200
	}
	//특정 유저가 특정 게시물에 북마크했는지 체크
	@GetMapping("/exists")
	public ResponseEntity<Boolean> isBookmarked(@PathVariable int postId, @RequestParam int userId){
		boolean isBookmarked = bookmarkService.isBookmarked(postId, userId);
		return ResponseEntity.ok(isBookmarked); // 200
	}
}
