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
		//회원가입 시 프로필 이미지는 필수
		String profileImg = user.getProfileImg();
		
		if (profileImg == null || profileImg.trim().isEmpty()) {
		    throw new CustomException(ErrorCode.INVALID_PROFILE_IMAGE);
		}
		return userDao.signupUser(user);
	}

	@Override
	public UserResponse getUserProfile(int userId) {
		
		UserResponse user = userDao.selectUserById(userId);
		
		// 해당 번호의 유저가 없으면 USER_NOT_FOUNT 예외처리
		if(user == null) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		
		// 게시물 수 조회
	    int postCount = userDao.countPostsByUserId(userId);
	    user.setPostCount(postCount);
	    
	    //userId 설정 추가함
	    user.setUserId(userId);
		
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
		
		// 게시물 수 조회 (추가)
	    int postCount = userDao.countPostsByUserId(user.getUserId());
	    
		return new UserResponse(
				user.getName(),
				user.getNickname(),
				user.getEmail(),
				user.getProfileImg(),
				user.getTierLevel(),
				postCount, // 조회한 postCount만 사용 (필드에 따로 없음)
				user.getUserId()
		);
	}
	public boolean isEmailDuplicate(String email) {
		User user = userDao.selectUserByEmail(email);
		//유저가 존재하면 중복 (true) 없으면 사용 가능(false)
		return user != null;
	}
	
	public boolean isNicknameDuplicate(String nickname) {
		User user = userDao.selectUserByNickname(nickname);
		//유저가 존재하면 중복 (true) 없으면 사용 가능(false)
		return user != null; 
	}
	
	
	

}
