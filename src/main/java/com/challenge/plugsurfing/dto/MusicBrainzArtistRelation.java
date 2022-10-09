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
public class MusicBrainzArtistRelation {

  private String type;
  private RelationUrl url;

  @JsonCreator
  public MusicBrainzArtistRelation(@JsonProperty("type") final String type, @JsonProperty("url") RelationUrl url) {
    this.type = type;
    this.url = url;
  }
}
