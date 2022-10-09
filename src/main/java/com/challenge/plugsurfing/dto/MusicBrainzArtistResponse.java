package com.challenge.plugsurfing.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicBrainzArtistResponse {

  private String name;

  private String gender;

  private String country;

  private List<MusicBrainzArtistRelation> musicBrainzArtistRelations;

  private List<MusicBrainzArtistReleaseGroup> musicBrainzArtistReleaseGroups;

  @JsonCreator
  public MusicBrainzArtistResponse(
      @JsonProperty("name") final String name,
      @JsonProperty("gender") final String gender,
      @JsonProperty("country") final String country,
      @JsonProperty("relations") final List<MusicBrainzArtistRelation> musicBrainzArtistRelations,
      @JsonProperty("release-groups") final List<MusicBrainzArtistReleaseGroup> musicBrainzArtistReleaseGroups) {
    this.name = name;
    this.gender = gender;
    this.country = country;
    this.musicBrainzArtistRelations = musicBrainzArtistRelations;
    this.musicBrainzArtistReleaseGroups = musicBrainzArtistReleaseGroups;
  }
}
