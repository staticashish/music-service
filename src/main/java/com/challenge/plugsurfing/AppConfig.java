package com.challenge.plugsurfing;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class AppConfig {

	@Bean
	public RestTemplate restTemaplate() {
		return new RestTemplateBuilder().build();
	}
}
