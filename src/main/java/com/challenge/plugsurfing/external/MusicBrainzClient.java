package com.challenge.plugsurfing.external;

import java.net.URI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.challenge.plugsurfing.dto.MusicBrainzArtistResponse;
import com.challenge.plugsurfing.external.errorhandler.MusicBrainzClientErrorHandler;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MusicBrainzClient {

  private static final String ARTIST_BASE_PATH = "/artist";
  private String baseUrl;
  private RestTemplate restTemplate;

  public MusicBrainzClient(
      @Value("${music-service.client.music-brainz.base-url}") final String baseUrl,
      @Qualifier("music-brainz-resttemplate") final RestTemplate restTemplate,
      final MusicBrainzClientErrorHandler errorHandler) {
    this.baseUrl = baseUrl;
    this.restTemplate = restTemplate;
    this.restTemplate.setErrorHandler(errorHandler);
  }

  public MusicBrainzArtistResponse getArtistInformationByMBID(String mbid) {
    URI musicBrainzArtistUrl = getMusicBrainzArtistUri(mbid);
    ResponseEntity<MusicBrainzArtistResponse> musicBrainzArtistResponse =
        restTemplate.getForEntity(musicBrainzArtistUrl, MusicBrainzArtistResponse.class);
    return musicBrainzArtistResponse.getBody();
  }

  private URI getMusicBrainzArtistUri(String mbid) {
    final String uri = String.format("%s/%s", baseUrl, ARTIST_BASE_PATH);
    return UriComponentsBuilder.fromUriString(uri)
        .pathSegment("{mbid}")
        .queryParam("fmt", "json")
        .queryParam("inc", "url-rels+release-groups")
        .buildAndExpand(mbid)
        .encode()
        .toUri();
  }
}
