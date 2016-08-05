package com.pushtech.crawler.connection;

import java.io.IOException;
import java.util.HashMap;
import static com.pushtech.crawler.logging.LoggingHelper.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

public class ConnectionHandler {

	public static HttpResponse getResponse(String url,
			HashMap<String, String> parameters,
			HashMap<String, String> headers, EngineContext.MethodType method) {

		HttpResponse response = null;
		
		
		
		EngineConnection engineConnection = EngineConnection
				.getDefaultEngineConnection();
		EngineContext engineContext = EngineContext.getEngineContext(url,
				parameters, headers, engineConnection);

		try {
			response = engineConnection.getHttpClient().execute(
					engineContext.getFormedRequest(engineContext, method),
					engineContext.createDefaultPolicy());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage());
		}

		return response;
	}
}
