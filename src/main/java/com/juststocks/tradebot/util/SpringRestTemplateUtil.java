/**
 * 
 */
package com.juststocks.tradebot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author bharath_kandasamy
 *
 */
@Service
public class SpringRestTemplateUtil extends RestTemplate {
	
	public RestTemplate restTemplate;

	@Autowired
	public SpringRestTemplateUtil(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
}
