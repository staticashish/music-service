package com.challenge.plugsurfing.external;

import java.net.URI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.challenge.plugsurfing.dto.WikipediaResponse;
import com.challenge.plugsurfing.external.errorhandler.WikipediaClientErrorHandler;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class WikipediaClient {

  private static final String PAGE_SUMMARY_PATH = "/page/summary";
  private String baseUrl;
  private RestTemplate restTemplate;

  public WikipediaClient(
      @Value("${music-service.client.wikipedia.base-url}") final String baseUrl,
      @Qualifier("wikipedia-resttemplate") final RestTemplate restTemplate,
      final WikipediaClientErrorHandler errorHandler) {
    this.baseUrl = baseUrl;
    this.restTemplate = restTemplate;
    this.restTemplate.setErrorHandler(errorHandler);
  }


  public WikipediaResponse getArtistWikipediaInformationByTitle(String artistTitle) {
    URI wikipediaUrl = getWikipediaUri(artistTitle);
    log.info("wikipediaUrl {}", wikipediaUrl);
    ResponseEntity<WikipediaResponse> artistWikipediaResponse = restTemplate.getForEntity(wikipediaUrl, WikipediaResponse.class);
    return artistWikipediaResponse.getBody();
  }

  private URI getWikipediaUri(String artistTitle) {
    final String uri = String.format("%s/%s", baseUrl, PAGE_SUMMARY_PATH);
    return UriComponentsBuilder.fromUriString(uri).pathSegment("{title}").buildAndExpand(artistTitle).encode().toUri();
  }
}
