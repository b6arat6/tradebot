package com.juststocks.tradebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.juststocks.tradebot.bean.TradebotProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteClientFacade;

@SpringBootApplication
public class TradebotApplication implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradebotApplication.class);

	public static ConfigurableApplicationContext context;
	
	public static TradebotApplication tradebotApplication;
	
	@Autowired
	public TradebotProperties properties;
	
	@Autowired
	public KiteClientFacade kiteClientFacade;
	
	public static void main(String[] args) {
		context = SpringApplication.run(TradebotApplication.class);
		tradebotApplication = (TradebotApplication) context.getBean(BEAN_TRADEBOT_APPLICATION);
		tradebotApplication.init();
	}
	
	public boolean init() {
		kiteClientFacade.login();
		return false;
	}
	
}
