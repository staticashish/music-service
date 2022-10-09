package com.challenge.plugsurfing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
@EnableAsync
public class AppConfig {

  @Bean
  @Qualifier("music-brainz-resttemplate")
  public RestTemplate restTemaplateMusicBrainz() {
    return new RestTemplateBuilder().build();
  }

  @Bean
  @Qualifier("wikidata-resttemplate")
  public RestTemplate restTemaplateWikidata() {
    return new RestTemplateBuilder().build();
  }

  @Bean
  @Qualifier("wikipedia-resttemplate")
  public RestTemplate restTemaplateWikipedia() {
    return new RestTemplateBuilder().build();
  }

  @Bean
  @Qualifier("cover-art-resttemplate")
  public RestTemplate restTemaplateCoverArt() {
    return new RestTemplateBuilder().build();
  }
}
