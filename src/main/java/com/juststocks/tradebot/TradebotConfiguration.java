/**
 * 
 */
package com.juststocks.tradebot;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juststocks.tradebot.bean.TradebotProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KCFacade;
import com.juststocks.tradebot.util.ApacheHttpUtil;

/**
 * @author bharath_kandasamy
 *
 */
@Configuration
@EnableConfigurationProperties( TradebotProperties.class  )
public class TradebotConfiguration implements TradebotConstants {
	
	@Bean( name=BEAN_TRADEBOT_PROPERTIES )
	public TradebotProperties tradebotProperties() {
		return new TradebotProperties();
	}
	
	@Bean( name=BEAN_KC_FACADE )
	public KCFacade kCFacade() {
		return new KCFacade();
	}
	
	@Bean( name=BEAN_APACHE_HTTP_UTIL )
	public ApacheHttpUtil<CloseableHttpResponse> apacheHttpUtil() {
		return new ApacheHttpUtil<CloseableHttpResponse>();
	}

}
