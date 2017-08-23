package com.juststocks.tradebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootApplication
public class TradebotApplication implements TradebotConstants {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradebotApplication.class);

	private static ConfigurableApplicationContext context;
	
	private static TradebotApplication tradebotApplication;
	
	@Autowired
	private ZerodhaTradebot zerodhaTradebot;
	
	public static void main(String[] args) {
		LOGGER.info(LOG_METHOD_ENTRY);
		context = SpringApplication.run(TradebotApplication.class);
		tradebotApplication = (TradebotApplication) context.getBean(BEAN_TRADEBOT_APPLICATION);
		tradebotApplication.zerodhaTradebot.run(args);
		LOGGER.info(LOG_METHOD_EXIT);
	}
	
}
