/**
 * 
 */
package com.juststocks.tradebot.facade;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.juststocks.tradebot.bean.TradebotProperties;
import com.juststocks.tradebot.bean.response.KiteClientLoginData;
import com.juststocks.tradebot.bean.response.KiteClientResponse;
import com.juststocks.tradebot.util.ApacheHttpUtil;
import com.rainmatter.kiteconnect.KiteConnect;

/**
 * @author bharath_kandasamy
 *
 */
@Component
public class KiteClientFacade implements GenericClientFacade {
	
	@Autowired
	public TradebotProperties properties;
	
	@Autowired
	public ApacheHttpUtil<CloseableHttpResponse> apacheHttpUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public boolean login() {
		KiteConnect kiteConnect = new KiteConnect(properties.getKcApikey());
		@SuppressWarnings("unchecked")
		KiteClientResponse<KiteClientLoginData> response = (KiteClientResponse<KiteClientLoginData>) restTemplate.getForObject(properties.getLoginEndpoint(), KiteClientResponse.class);
		System.out.println(response.toString());
		return false;
	}

	@Override
	public boolean authenticate() {
		return false;
	}

}
