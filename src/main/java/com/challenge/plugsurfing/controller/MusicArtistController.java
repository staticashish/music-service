package com.challenge.plugsurfing.controller;

import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.challenge.plugsurfing.dto.MusicArtistResponse;
import com.challenge.plugsurfing.service.MusicArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/music-artist")
@Slf4j
@Tag(name = "Music-Artist", description = "Endpoints for managing music artist information")
public class MusicArtistController {

  @Autowired
  private MusicArtistService musicArtistService;

  @GetMapping("/{mbid}")
  @Operation(summary = "Finds a music artist information", description = "Finds a music artist information by their musicbrainz id (mbid).",
      tags = {"Music-Artist"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = MusicArtistResponse.class))),
          @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)})
  public MusicArtistResponse getMusicArtist(@NonNull @PathVariable("mbid") final String mbid) {
    Instant start = Instant.now();
    MusicArtistResponse musicArtistResponse = musicArtistService.getMusicArtistInformation(mbid);
    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();
    log.info("Total time: {} ms", timeElapsed); // 37590 ms
    return musicArtistResponse;
  }
}
