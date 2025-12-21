package com.own.domain.user.dto.model;

import java.time.LocalDateTime;

public class User {

	private int userId;
	private String name;
	private String nickname;
	private String email;
	private String password;
	private String profileImg;
	private int tierLevel;
	private LocalDateTime deletedAt;
	private boolean status;
	
	
	public int getUserId() {
		return userId;
	}
	
	public User() {
		
	}
	
	public User(int userId, String name, String nickname, String email, String password, String profileImg,
			int tierLevel, LocalDateTime deletedAt, boolean status) {
		this.userId = userId;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.profileImg = profileImg;
		this.tierLevel = tierLevel;
		this.deletedAt = deletedAt;
		this.status = status;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public int getTierLevel() {
		return tierLevel;
	}
	public void setTierLevel(int tierLevel) {
		this.tierLevel = tierLevel;
	}
	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
