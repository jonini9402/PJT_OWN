package com.own.domain.music.service;

import com.own.domain.music.dto.request.MusicSaveRequest;
import com.own.domain.music.dto.response.MusicSearchResponse;

public interface MusicService {
	
	// 현재 음악이 DB에 있으면 재사용, 없으면 DB에 새로 저장 
	MusicSearchResponse getOrSaveMusic(MusicSaveRequest request);
	
}
