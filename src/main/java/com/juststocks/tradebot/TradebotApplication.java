package com.juststocks.tradebot;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.juststocks.tradebot.bean.TradebotProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KCFacade;
import com.juststocks.tradebot.util.ApacheHttpUtil;

@SpringBootApplication
@EnableConfigurationProperties( { TradebotProperties.class } )
public class TradebotApplication implements TradebotConstants {
	
	public static ConfigurableApplicationContext context;

	public static TradebotProperties properties;
	
	public static KCFacade kcFacade;
	
	public static ApacheHttpUtil<CloseableHttpResponse> apacheHttpUtil;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		context = SpringApplication.run(TradebotConfiguration.class, args);
		properties = (TradebotProperties) context.getBean(BEAN_TRADEBOT_PROPERTIES);
		kcFacade = (KCFacade) context.getBean(BEAN_KC_FACADE);
		apacheHttpUtil = (ApacheHttpUtil<CloseableHttpResponse>) context.getBean(BEAN_APACHE_HTTP_UTIL);
	}
	
}
