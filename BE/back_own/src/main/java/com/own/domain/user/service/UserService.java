package com.own.domain.user.service;

import com.own.domain.user.dto.request.UserLoginRequest;
import com.own.domain.user.dto.request.UserSignupRequest;
import com.own.domain.user.dto.request.UserUpdateRequest;
import com.own.domain.user.dto.response.UserResponse;
import com.own.domain.user.dto.response.UserTierResponse;

import jakarta.servlet.http.HttpSession;

public interface UserService {
	
	// 회원가입
	boolean signupUser(UserSignupRequest user);
	
	// 프로필 조회
	UserResponse getUserProfile(int userId);
	
	// 프로필 수정
	boolean updateUser(int userId, UserUpdateRequest user);
	
	// 회원 탈퇴
	boolean deleteUser(int userId);
	
	// 유저등급 조회
	UserTierResponse getUserTier(int userId);
	
	// 로그인
	UserResponse login(UserLoginRequest request, HttpSession session);
		
	
}
