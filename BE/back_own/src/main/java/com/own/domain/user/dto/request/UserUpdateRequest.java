package com.own.domain.user.dto.request;

public class UserUpdateRequest {
	
	private String profileImg;
	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirm;
	
	
	public UserUpdateRequest() {
		
	}
	
	public UserUpdateRequest(String profileImg, String oldPassword, String newPassword, String newPasswordConfirm) {
		this.profileImg = profileImg;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.newPasswordConfirm = newPasswordConfirm;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}
	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	
	
}
