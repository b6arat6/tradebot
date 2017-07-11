/**
 * 
 */
package com.juststocks.tradebot.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author bharath_kandasamy
 *
 */
public class ApacheHttpUtil<CloseableHttpResponse> implements HttpUtil<CloseableHttpResponse> {
	
	private static final CloseableHttpClient CLOSEABLE_HTTP_CLIENT = HttpClients.createDefault();

	@SuppressWarnings("unchecked")
	@Override
	public CloseableHttpResponse executeGet(String uri) {
		HttpGet get = new HttpGet(uri);
		CloseableHttpResponse response = null;
		try {
			response = (CloseableHttpResponse) CLOSEABLE_HTTP_CLIENT.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public CloseableHttpResponse executePost() {
		return null;
	}

}
