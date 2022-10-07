package com.challenge.plugsurfing.external.errorhandler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Component
public class WikipediaClientErrorHandler extends DefaultResponseErrorHandler {

	@Override
	protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
		super.handleError(response, statusCode);
	}
}
