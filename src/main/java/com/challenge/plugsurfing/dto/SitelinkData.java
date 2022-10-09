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
public class SitelinkData {

  private String site;
  private String title;
  private String url;

  @JsonCreator
  public SitelinkData(@JsonProperty("site") final String site, @JsonProperty("title") final String title, @JsonProperty("url") final String url) {
    super();
    this.site = site;
    this.title = title;
    this.url = url;
  }
}
