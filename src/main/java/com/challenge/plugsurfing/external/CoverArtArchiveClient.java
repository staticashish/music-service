package com.challenge.plugsurfing.external;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.plugsurfing.dto.ArtCover;
import com.challenge.plugsurfing.dto.WikipediaResponse;
import com.challenge.plugsurfing.external.errorhandler.WikipediaClientErrorHandler;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CoverArtArchiveClient {

	private static final String RELEASE_GROUP_PATH = "release-group";
	private String baseUrl;
	private RestTemplate restTemplate;

	public CoverArtArchiveClient(@Value("${music-service.client.cover-art.base-url}") final String baseUrl,
			final RestTemplate restTemplate, final WikipediaClientErrorHandler errorHandler) {
		this.baseUrl = baseUrl;
		this.restTemplate = restTemplate;
		this.restTemplate.setErrorHandler(errorHandler);
	}
	
	public ArtCover getArtCoverByMBID(String mbid) {
		URI wikipediaUrl = getWikipediaUri(mbid);
		log.info("wikipediaUrl {}", wikipediaUrl);
		ResponseEntity<ArtCover> artCoverResponse = restTemplate.getForEntity(wikipediaUrl, ArtCover.class);
		return artCoverResponse.getBody();
	}
	
	private URI getWikipediaUri(String mbid) {
		final String uri = String.format("%s/%s", baseUrl, RELEASE_GROUP_PATH);
        return UriComponentsBuilder.fromUriString(uri)
        		.pathSegment("{mbid}")
        		.buildAndExpand(mbid)
                .encode().toUri();
	}
}
