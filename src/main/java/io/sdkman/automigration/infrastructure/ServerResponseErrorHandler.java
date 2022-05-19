package io.sdkman.automigration.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class ServerResponseErrorHandler extends DefaultResponseErrorHandler {

	@Override
	protected boolean hasError(int unknownStatusCode) {
		HttpStatus.Series series = HttpStatus.Series.resolve(unknownStatusCode);
		return series == HttpStatus.Series.SERVER_ERROR;
	}

	@Override
	protected boolean hasError(HttpStatusCode statusCode) {
		return statusCode.is5xxServerError();
	}

}
