package com.own.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
	private int postId;
	private LocalDateTime createdAt;
	private String nickname;
	private String profileImg;
	private int tierLevel;
	
	private int workoutTag;
	private List <String> emotionTags; 
	private String caption;
	
	private String musicTitle;
	private String albumImg;
	private String artist;
	private int duration;
	private String previewUrl;
	
	private int likeCnt;
	private int bookmarkCnt;
	private boolean isLiked;
	private boolean isBookmarked;
	
	public PostResponse() {
	}
	
	public PostResponse(int postId, LocalDateTime createdAt, String nickname, String profileImg, int tierLevel,
			int workoutTag, List<String> emotionTags, String caption, String musicTitle, String albumImg, String artist, int duration,
			String previewUrl, int likeCnt, int bookmarkCnt, boolean isLiked, boolean isBookmarked) {
		this.postId = postId;
		this.createdAt = createdAt;
		this.nickname = nickname;
		this.profileImg = profileImg;
		this.tierLevel = tierLevel;
		this.workoutTag = workoutTag;
		this.emotionTags = emotionTags;
		this.caption = caption;
		this.musicTitle = musicTitle;
		this.albumImg = albumImg;
		this.artist = artist;
		this.duration = duration;
		this.previewUrl = previewUrl;
		this.likeCnt = likeCnt;
		this.bookmarkCnt = bookmarkCnt;
		this.isLiked = isLiked;
		this.isBookmarked = isBookmarked;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public int getWorkoutTag() {
		return workoutTag;
	}
	public void setWorkoutTag(int workoutTag) {
		this.workoutTag = workoutTag;
	}
	public List<String> getEmotionTags() {
		return emotionTags;
	}
	public void setEmotionTags(List<String> emotionTags) {
		this.emotionTags = emotionTags;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getMusicTitle() {
		return musicTitle;
	}
	public void setMusicTitle(String musicTitle) {
		this.musicTitle = musicTitle;
	}
	public String getAlbumImg() {
		return albumImg;
	}
	public void setAlbumImg(String albumImg) {
		this.albumImg = albumImg;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public int getBookmarkCnt() {
		return bookmarkCnt;
	}
	public void setBookmarkCnt(int bookmarkCnt) {
		this.bookmarkCnt = bookmarkCnt;
	}
	public boolean isLiked() {
		return isLiked;
	}
	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	public boolean isBookmarked() {
		return isBookmarked;
	}
	public void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}
	
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "PostResponse [postId=" + postId + ", createdAt=" + createdAt + ", nickname=" + nickname
				+ ", profileImg=" + profileImg + ", tierLevel=" + tierLevel + ", workoutTag=" + workoutTag
				+ ", emotionTags=" + emotionTags + ", caption=" + caption + ", musicTitle=" + musicTitle + ", albumImg="
				+ albumImg + ", duration=" + duration + ", previewUrl=" + previewUrl + ", likeCnt=" + likeCnt
				+ ", bookmarkCnt=" + bookmarkCnt + ", isLiked=" + isLiked + ", isBookmarked=" + isBookmarked + "]";
	}
	
	
}
