package com.challenge.plugsurfing.dto;

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
public class WikidataResponse {

	private Map<String, WikiEntity> entities;

	@JsonCreator
	public WikidataResponse(@JsonProperty("entities") Map<String, WikiEntity> entities) {
		super();
		this.entities = entities;
	}
	
}
