package com.challenge.plugsurfing.external.errorhandler;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import com.challenge.plugsurfing.exception.BadRequestException;
import com.challenge.plugsurfing.exception.DataNotFoundException;
import com.challenge.plugsurfing.exception.FatalException;

@Component
public class CoverArtClientErrorHandler extends DefaultResponseErrorHandler {

  @Override
  protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {

    switch (statusCode) {
      case NOT_FOUND:
        throw new DataNotFoundException(statusCode + "!!! Exception occurs while fetching CoverArt data");
      case BAD_REQUEST:
        throw new BadRequestException(statusCode + "!!! Exception occurs while fetching CoverArt data: Invalid id.");
      default:
        throw new FatalException(statusCode + "!!! " + "Exception occurs while fetching CoverArt data");
    }
  }
}
