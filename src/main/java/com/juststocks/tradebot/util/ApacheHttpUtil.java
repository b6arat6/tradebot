/**
 * 
 */
package com.juststocks.tradebot.util;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Resource;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
@Component
public class ApacheHttpUtil implements TradebotConstants {
	
	private static final CloseableHttpClient CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();

	public <T> void executeGet(String uri, ResponseHandler<T> responseHandler) {
		HttpGet get = new HttpGet(uri);
		try {
			CLOSEABLE_HTTP_CLIENT.execute(get, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CloseableHttpResponse executePost() {
		return null;
	}

	public static class RedirectResponseHandler<T> implements ResponseHandler<T> {
		@Override
		public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			if (response.getStatusLine().getStatusCode() == HTTP_STATUS_CODE_REDIRECT) {
				String uri = response.getFirstHeader(HTTP_HEADER_LOCATION).getValue();
			}
			return null;
		}
		
	}
	
}