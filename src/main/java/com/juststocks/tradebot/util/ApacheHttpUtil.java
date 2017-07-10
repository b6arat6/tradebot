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
	
	private static final CloseableHttpClient CLIENT = HttpClients.createDefault();

	@Override
	public CloseableHttpResponse executeGetRequest(String uri) {
		HttpGet get = new HttpGet(uri);
		try {
			CloseableHttpResponse response = (CloseableHttpResponse) CLIENT.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CloseableHttpResponse executePostRequest() {
		return null;
	}

}
