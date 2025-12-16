package com.own.domain.music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.domain.music.dao.MusicDao;
import com.own.domain.music.dto.model.Music;
import com.own.domain.music.dto.request.MusicSaveRequest;
import com.own.domain.music.dto.response.MusicSearchResponse;

@Service
public class MusicServiceImpl implements MusicService {

	@Autowired
	private MusicDao musicDao;
	
	@Override
	public MusicSearchResponse getOrSaveMusic(MusicSaveRequest request) {
		
		Music music = musicDao.findBySpotifyTrackId(request.getSpotifyTrackId());
		
		if (music == null) {
			musicDao.insertMusic(request);
			music = musicDao.findBySpotifyTrackId(request.getSpotifyTrackId());
		}
		
		return new MusicSearchResponse(
				music.getMusicId(),
				music.getMusicTitle(),
				music.getArtist(),
				music.getAlbumImg(),
				music.getSpotifyTrackId(),
				music.getDuration(),
				music.getPreviewUrl()
				
		);
	}

}
