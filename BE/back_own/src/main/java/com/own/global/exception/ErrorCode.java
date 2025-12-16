package com.own.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	
	
	// 백엔드 코드 작성하면서 필요한 에러 추가하기//
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 요청 값입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
	// 북마크 관련 비즈니스 오류
    ALREADY_BOOKMARKED(HttpStatus.CONFLICT, "이미 북마크된 게시물입니다."), // HTTP 409 Conflict
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 북마크를 찾을 수 없습니다."), // HTTP 404 Not Found
	//좋아요 관련 비즈니스 오류
	ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요된 게시물입니다."), // HTTP 409
	LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 좋아요를 찾을 수 없습니다."); // HTTP 404 Not Found

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getMessage() { return message; }
}

