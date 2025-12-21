package com.own.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.domain.user.dto.request.UserLoginRequest;
import com.own.domain.user.dto.request.UserSignupRequest;
import com.own.domain.user.dto.request.UserUpdateRequest;
import com.own.domain.user.dto.response.UserResponse;
import com.own.domain.user.dto.response.UserTierResponse;
import com.own.domain.user.service.UserService;
import com.own.global.exception.CustomException;
import com.own.global.exception.ErrorCode;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<Boolean> signup(@RequestBody UserSignupRequest user) {

		boolean result = userService.signupUser(user);

		return ResponseEntity.ok(result);
	}

	// 프로필 조회
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable int userId) {

		UserResponse response = userService.getUserProfile(userId);

		return ResponseEntity.ok(response);
	}

	// 프로필 수정
	@PutMapping("/{userId}")
	public ResponseEntity<Boolean> update(@PathVariable int userId, @RequestBody UserUpdateRequest user) {

		boolean result = userService.deleteUser(userId);

		return ResponseEntity.ok(result);
	}

	// 회원탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<Boolean> delete(@PathVariable int userId) {

		boolean result = userService.deleteUser(userId);

		return ResponseEntity.ok(result);
	}

	// 유저 등급 조회
	@GetMapping("/{userId}/tier")
	public ResponseEntity<UserTierResponse> getTier(@PathVariable int userId) {

		UserTierResponse tier = userService.getUserTier(userId);

		return ResponseEntity.ok(tier);
	}

	// 로그인
	@PostMapping("/login")
	public UserResponse login(@RequestBody UserLoginRequest request, HttpSession session) {
		return userService.login(request, session);
	}

	// 로그아웃
	@PostMapping("/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	// 테스트용 내 정보 조회
	@GetMapping("/me")
	public UserResponse getMyInfo(HttpSession session) {

		Integer userId = (Integer) session.getAttribute("loginUserId");

		if (userId == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		return userService.getUserProfile(userId);
	}

	// 이메일 중복 체크
	@GetMapping("/check-email")
	public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam String email) {
		boolean isDuplicate = userService.isEmailDuplicate(email);
		return ResponseEntity.ok(isDuplicate);
	}

	// 닉네임 중복 체크
	@GetMapping("/check-nickname")
	public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
		boolean isDuplicate = userService.isNicknameDuplicate(nickname);
		return ResponseEntity.ok(isDuplicate);
	}
}
