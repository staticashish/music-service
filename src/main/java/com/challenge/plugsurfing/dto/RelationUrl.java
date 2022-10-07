package com.challenge.plugsurfing.dto;

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
public class RelationUrl {
	
	private String resource;
	private String id;
	
	@JsonCreator
	public RelationUrl(@JsonProperty("resource") final String resource,@JsonProperty("id") String id) {
		super();
		this.resource = resource;
		this.id = id;
	}
	
	
}
