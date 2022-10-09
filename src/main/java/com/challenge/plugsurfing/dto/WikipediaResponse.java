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
public class WikipediaResponse {

  private String title;
  private String description;
  private String extract;

  @JsonCreator
  public WikipediaResponse(
      @JsonProperty("title") final String title,
      @JsonProperty("description") final String description,
      @JsonProperty("extract") final String extract) {
    super();
    this.title = title;
    this.description = description;
    this.extract = extract;
  }
}
