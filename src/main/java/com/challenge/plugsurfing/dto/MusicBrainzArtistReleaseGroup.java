package com.challenge.plugsurfing.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicBrainzArtistReleaseGroup implements Serializable {

	private String title;
	
	private String type;
	
	private String releaseDate;
	
	private String mbid;

	private ArtCover artCover;
	
	@JsonCreator
	public MusicBrainzArtistReleaseGroup(@JsonProperty("title") final String title,
			@JsonProperty("primary-type") String type,
			@JsonProperty("first-release-date") final String releaseDate,
			@JsonProperty("id") final String mbid,
			@JsonProperty("artCover") final ArtCover artCover) {
		super();
		this.title = title;
		this.type = type;
		this.releaseDate = releaseDate;
		this.mbid = mbid;
		this.artCover = artCover;
	}
}