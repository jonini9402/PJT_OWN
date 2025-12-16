package com.own.domain.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.domain.music.dto.request.MusicSaveRequest;
import com.own.domain.music.dto.response.MusicSearchResponse;
import com.own.domain.music.service.MusicService;

@RestController
@RequestMapping("/api/music")
public class MusicController {
	
	@Autowired
	private MusicService musicService;
	
	@PostMapping
	public MusicSearchResponse saveMusic(@RequestBody MusicSaveRequest request) {
		
		return musicService.getOrSaveMusic(request);
		
	}
	
}
