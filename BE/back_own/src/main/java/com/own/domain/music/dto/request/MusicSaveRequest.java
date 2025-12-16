package com.own.domain.music.dto.request;

public class MusicSaveRequest {
	private Integer musicId;
	private String musicTitle;
	private String artist;
	private String albumImg;
	private String spotifyTrackId; 
	private int duration;
	private String previewUrl;
	
	public MusicSaveRequest(Integer musicId, String musicTitle, String artist, String albumImg, String spotifyTrackId,
			int duration, String previewUrl) {
		super();
		this.musicId = musicId;
		this.musicTitle = musicTitle;
		this.artist = artist;
		this.albumImg = albumImg;
		this.spotifyTrackId = spotifyTrackId;
		this.duration = duration;
		this.previewUrl = previewUrl;
	}
	
	public Integer getMusicId() {
		return musicId;
	}
	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}
	public String getMusicTitle() {
		return musicTitle;
	}
	public void setMusicTitle(String musicTitle) {
		this.musicTitle = musicTitle;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbumImg() {
		return albumImg;
	}
	public void setAlbumImg(String albumImg) {
		this.albumImg = albumImg;
	}
	public String getSpotifyTrackId() {
		return spotifyTrackId;
	}
	public void setSpotifyTrackId(String spotifyTrackId) {
		this.spotifyTrackId = spotifyTrackId;
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

	@Override
	public String toString() {
		return "MusicSaveRequest [musicId=" + musicId + ", musicTitle=" + musicTitle + ", artist=" + artist
				+ ", albumImg=" + albumImg + ", spotifyTrackId=" + spotifyTrackId + ", duration=" + duration
				+ ", previewUrl=" + previewUrl + "]";
	}
	
	
}
