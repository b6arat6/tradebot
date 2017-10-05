package com.juststocks.tradebot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	private static boolean shutdown = false;
	private final static BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

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
			try {
				BUFFERED_READER.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tradeableTickDataLoggingActorCancellable.cancel();
			actorSystem.terminate();
			LOGGER.info(SHUTDOWN_COMPLETE);
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		LOGGER.info(METHOD_ENTRY);
		
		context = SpringApplication.run(TradebotApplication.class);
		
		tradebotApplication = (TradebotApplication) context.getBean(BEAN_TRADEBOT_APPLICATION);
		
		Runtime.getRuntime().addShutdownHook(tradebotApplication.new ShutdownHook());
		
		Thread zerodhaTradebotThread = new Thread(new Runnable() {
			@Override
			public void run() {
				if (!tradebotApplication.zerodhaTradebot.run(args)) {
					SpringApplication.exit(context, tradebotApplication);
					System.exit(-1);
				}
			}
		});
		zerodhaTradebotThread.start();
		
		System.err.println(ENTER_COMMAND);
		while (!shutdown) {
			switch (Integer.valueOf(BUFFERED_READER.readLine())) {
			case COMMAND_SHUTDOWN:
				shutdown = true;
				zerodhaTradebotThread.interrupt();
				System.exit(-1);
				break;
			case COMMAND_TRADE_OHL:
				tradebotApplication.zerodhaTradebot.tradeSystemFacade.triggerTrades(ACTOR_TRADE_GENERATOR_MSG_TYPE_TRADE_OHL);
				break;
			default:
				break;
			}
		}
		
		LOGGER.info(METHOD_EXIT);
	}

	@Override
	public int getExitCode() {
		return 0;
	}
	
}
