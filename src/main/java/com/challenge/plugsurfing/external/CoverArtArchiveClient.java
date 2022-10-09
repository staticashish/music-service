package com.challenge.plugsurfing.external;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.challenge.plugsurfing.dto.ArtCover;
import com.challenge.plugsurfing.exception.DataNotFoundException;
import com.challenge.plugsurfing.external.errorhandler.CoverArtClientErrorHandler;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CoverArtArchiveClient {

  private static final String RELEASE_GROUP_PATH = "release-group";
  private String baseUrl;
  private RestTemplate restTemplate;

  public CoverArtArchiveClient(
      @Value("${music-service.client.cover-art.base-url}") final String baseUrl,
      @Qualifier("cover-art-resttemplate") final RestTemplate restTemplate,
      final CoverArtClientErrorHandler errorHandler) {
    this.baseUrl = baseUrl;
    this.restTemplate = restTemplate;
    this.restTemplate.setErrorHandler(errorHandler);
  }

  public Optional<ArtCover> getArtCoverByMBID(String mbid) {
    URI coverArtUrl = getCoverArtUrl(mbid);
    log.info("CoverArtUrl {}", coverArtUrl);
    ResponseEntity<ArtCover> artCoverResponse = null;
    try {
      artCoverResponse = restTemplate.getForEntity(coverArtUrl, ArtCover.class);
      return Optional.of(artCoverResponse.getBody());
    } catch (DataNotFoundException e) {
      log.error("Cover Art data not found for mbid {}", mbid);
    }
    return Optional.empty();
  }

  private URI getCoverArtUrl(String mbid) {
    final String uri = String.format("%s/%s", baseUrl, RELEASE_GROUP_PATH);
    return UriComponentsBuilder.fromUriString(uri).pathSegment("{mbid}").buildAndExpand(mbid).encode().toUri();
  }
}
