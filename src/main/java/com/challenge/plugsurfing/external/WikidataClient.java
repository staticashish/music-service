package com.challenge.plugsurfing.external;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.plugsurfing.dto.MusicArtistResponse;
import com.challenge.plugsurfing.dto.MusicBrainzArtistResponse;
import com.challenge.plugsurfing.dto.WikidataResponse;
import com.challenge.plugsurfing.external.errorhandler.MusicBrainzClientErrorHandler;
import com.challenge.plugsurfing.external.errorhandler.WikidataClientErrorHandler;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class WikidataClient {

	private static final String ENTITY_DATA_PATH = "/Special:EntityData";
	private String baseUrl;
	private RestTemplate restTemplate;

	public WikidataClient(@Value("${music-service.client.wikidata.base-url}") final String baseUrl,
			final RestTemplate restTemplate, final WikidataClientErrorHandler errorHandler) {
		this.baseUrl = baseUrl;
		this.restTemplate = restTemplate;
		this.restTemplate.setErrorHandler(errorHandler);
	}
	
	public WikidataResponse getArtistWikidataInformationByWikiId(String wikiId) {
		URI wikiDataUrl = getWikidataUri(wikiId);
		ResponseEntity<WikidataResponse> artistWikiDataResponse = restTemplate.getForEntity(wikiDataUrl, WikidataResponse.class);
		return artistWikiDataResponse.getBody();
	}
	
	private URI getWikidataUri(String wikiId) {
		final String uri = String.format("%s/%s", baseUrl, ENTITY_DATA_PATH);
        return UriComponentsBuilder.fromUriString(uri)
        		.pathSegment("{wikiId}")
        		.buildAndExpand(wikiId+".json")
                .encode().toUri();
	}
}
