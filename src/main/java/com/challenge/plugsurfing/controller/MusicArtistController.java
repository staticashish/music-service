package com.challenge.plugsurfing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.plugsurfing.dto.MusicArtistResponse;
import com.challenge.plugsurfing.service.MusicArtistService;

@RestController
@RequestMapping(path = "/music-artist")
public class MusicArtistController {

	@Autowired
	private MusicArtistService musicArtistService;
	
	@GetMapping("/{mbid}")
	public MusicArtistResponse getMusicArtist(@PathVariable("mbid") final String mbid) {
		return musicArtistService.getMusicArtistInformation(mbid);
	}
}
