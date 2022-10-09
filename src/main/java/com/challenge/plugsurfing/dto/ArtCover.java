package com.challenge.plugsurfing.dto;

import java.io.Serializable;
import java.util.List;
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
public class ArtCover implements Serializable {

  private List<Image> images;

  @JsonCreator
  public ArtCover(@JsonProperty("images") final List<Image> images) {
    this.images = images;
  }
}
