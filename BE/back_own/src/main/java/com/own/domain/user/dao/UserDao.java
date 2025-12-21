package com.own.domain.user.dao;

import com.own.domain.user.dto.model.User;
import com.own.domain.user.dto.request.UserSignupRequest;
import com.own.domain.user.dto.request.UserUpdateRequest;
import com.own.domain.user.dto.response.UserResponse;
import com.own.domain.user.dto.response.UserTierResponse;

public interface UserDao {
	
	// 회원가입
	public boolean signupUser(UserSignupRequest user);
	
	// 프로필 조회
	public UserResponse selectUserById(int userId);
	
	// 프로필 수정
	public boolean updateUser(int userId, UserUpdateRequest user);
	
	// 회원탈퇴
	public boolean deleteUser(int userId);
	
	// 유저등급 조회
	public UserTierResponse selectUserTierById(int userId);
	
	// 로그인용 이메일로 조회
	User selectUserByEmail(String email);
	
	//로그인용 닉네임 조회
	User selectUserByNickname(String nickname);
}
