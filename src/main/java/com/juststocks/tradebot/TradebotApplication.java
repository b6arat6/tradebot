package com.juststocks.tradebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.juststocks.tradebot.constants.TradebotConstants;

import akka.actor.ActorSystem;
import akka.actor.Cancellable;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootApplication
public class TradebotApplication implements TradebotConstants, ExitCodeGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_MAIN);

	private static ConfigurableApplicationContext context;
	
	private static TradebotApplication tradebotApplication;
	
	@Autowired
	private ZerodhaTradebot zerodhaTradebot;
	
	@Autowired
	private ActorSystem actorSystem;
	
	@Autowired
	@Qualifier(BEAN_AKKA_TRADEABLE_TICK_DATA_LOGGING_ACTOR_CANCELLABLE)
	Cancellable tradeableTickDataLoggingActorCancellable;
	
	private class ShutdownHook extends Thread {
		@Override
		public void run() {
			LOGGER.info(SHUTTING_DOWN);
			tradeableTickDataLoggingActorCancellable.cancel();
			actorSystem.terminate();
			LOGGER.info(SHUTDOWN_COMPLETE);
		}
	}
	
	public static void main(String[] args) {
		LOGGER.info(METHOD_ENTRY);
		
		context = SpringApplication.run(TradebotApplication.class);
		
		tradebotApplication = (TradebotApplication) context.getBean(BEAN_TRADEBOT_APPLICATION);
		
		Runtime.getRuntime().addShutdownHook(tradebotApplication.new ShutdownHook());
		
		if (!tradebotApplication.zerodhaTradebot.run(args)) {
			SpringApplication.exit(context, tradebotApplication);
			System.exit(-1);
		}
		
		LOGGER.info(METHOD_EXIT);
	}

	@Override
	public int getExitCode() {
		return 0;
	}
	
}
