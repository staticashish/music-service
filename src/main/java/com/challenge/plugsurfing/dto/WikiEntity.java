package com.challenge.plugsurfing.dto;

import java.util.Map;
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
public class WikiEntity {

  private Map<String, SitelinkData> sitelinks;

  @JsonCreator
  public WikiEntity(@JsonProperty("sitelinks") final Map<String, SitelinkData> sitelinks) {
    super();
    this.sitelinks = sitelinks;
  }
}
