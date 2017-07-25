/**
 * 
 */
package com.juststocks.tradebot;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.juststocks.tradebot.bean.TradebotProperties;
import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootConfiguration
@EnableConfigurationProperties(TradebotProperties.class)
public class TradebotConfiguration implements TradebotConstants {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
