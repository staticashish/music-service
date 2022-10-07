package com.challenge.plugsurfing.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
public class Image implements Serializable{

	private List<String> types;
	private String image;
	private Map<String, String> thumbnails;

	@JsonCreator
	public Image(@JsonProperty("types") final List<String> types,
			@JsonProperty("image") final String image,
			@JsonProperty("thumbnails") final Map<String, String> thumbnails) {
		this.types = types;
		this.image = image;
		this.thumbnails = thumbnails;
	}
	
}
