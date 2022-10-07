package com.challenge.plugsurfing.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicArtistResponse implements Serializable {
	 
	private String artistTitle;
	private String artistTitleDesc;
	private String description;
	private List<MusicBrainzArtistReleaseGroup> albums;
	
	@JsonCreator
	public MusicArtistResponse(@JsonProperty("artistTitle") final String artistTitle,
			@JsonProperty("artistTitleDesc") final String artistTitleDesc,
			@JsonProperty("description") final String description,
			@JsonProperty("albums") final List<MusicBrainzArtistReleaseGroup> albums) {
		super();
		this.artistTitle = artistTitle;
		this.artistTitleDesc = artistTitleDesc;
		this.description = description;
		this.albums = albums;
	}
	
}
