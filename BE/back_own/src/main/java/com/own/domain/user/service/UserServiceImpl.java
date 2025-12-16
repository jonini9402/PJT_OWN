package com.own.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.domain.user.dao.UserDao;
import com.own.domain.user.dto.model.User;
import com.own.domain.user.dto.request.UserLoginRequest;
import com.own.domain.user.dto.request.UserSignupRequest;
import com.own.domain.user.dto.request.UserUpdateRequest;
import com.own.domain.user.dto.response.UserResponse;
import com.own.domain.user.dto.response.UserTierResponse;
import com.own.global.exception.CustomException;
import com.own.global.exception.ErrorCode;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean signupUser(UserSignupRequest user) {
		return userDao.signupUser(user);
	}

	@Override
	public UserResponse getUserProfile(int userId) {
		
		UserResponse user = userDao.selectUserById(userId);
		
		// 해당 번호의 유저가 없으면 USER_NOT_FOUNT 예외처리
		if(user == null) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		
		return user;
	}

	@Override
	public boolean updateUser(int userId, UserUpdateRequest user) {
		
		UserResponse origin = userDao.selectUserById(userId);
		
		// 해당 번호의 유저가 없으면 USER_NOT_FOUNT 예외처리
		if(origin == null) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		
		return userDao.updateUser(userId, user);
	}

	@Override
	public boolean deleteUser(int userId) {
		
		return userDao.deleteUser(userId);
	}

	@Override
	public UserTierResponse getUserTier(int userId) {
		
		return userDao.selectUserTierById(userId);
	}

	@Override
	public UserResponse login(UserLoginRequest request, HttpSession session) {
		
		User user = userDao.selectUserByEmail(request.getEmail());
		
		if (user == null) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		
		if(!user.getPassword().equals(request.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}
		
		// 세션에 로그인 정보 저장
		session.setAttribute("loginUserId", user.getUserId());		
		
		return new UserResponse(
				user.getName(),
				user.getNickname(),
				user.getEmail(),
				user.getProfileImg(),
				user.getTierLevel()
		);
	}

}
