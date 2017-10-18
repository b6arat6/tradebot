/**
 * 
 */
package com.juststocks.tradebot;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.juststocks.tradebot.actor.OHLStrategyActor;
import com.juststocks.tradebot.actor.TickDispenserActor;
import com.juststocks.tradebot.actor.TradeGeneratorActor;
import com.juststocks.tradebot.actor.TradeableTickDataLoggingActor;
import com.juststocks.tradebot.bean.ZerodhaProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeFacade;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.routing.SmallestMailboxPool;
import scala.concurrent.duration.Duration;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootConfiguration
@EnableScheduling
@EnableConfigurationProperties(ZerodhaProperties.class)
public class TradebotConfiguration implements TradebotConstants {
	
	@Autowired
	public ZerodhaProperties zerodhaProperties;
	
	@Autowired
	private KiteTradeFacade kiteTradeFacade;
	
	@Autowired
	private ActorSystem actorSystem;
	
	@Autowired
	@Qualifier(BEAN_AKKA_OHL_STRATEGY_ACTOR_REF)
	private ActorRef ohlTradeStrategyActorRef;
	
	@Bean(name=BEAN_AKKA_ACTOR_SYSTEM)
	public ActorSystem actorSystem() {
		return ActorSystem.create(BEAN_AKKA_ACTOR_SYSTEM);
	}
	
	@Bean(name=BEAN_AKKA_OHL_STRATEGY_ACTOR_REF)
	public ActorRef ohlStrategyActor() {
		return actorSystem.actorOf(OHLStrategyActor.props(zerodhaProperties, kiteTradeFacade)
				.withRouter(new SmallestMailboxPool(zerodhaProperties.getOhlStrategyActorRoutees())));
	}
	
	@Bean(name=BEAN_AKKA_TICK_DISPENSER_ACTOR_REF)
	public ActorRef tickDispenserActor() {
		return actorSystem.actorOf(TickDispenserActor.props(zerodhaProperties, ohlTradeStrategyActorRef)
				.withRouter(new SmallestMailboxPool(zerodhaProperties.getTickDispenserActorRoutees())));
	}
	
	@Bean(name=BEAN_AKKA_TRADE_GENERATOR_ACTOR_REF)
	public ActorRef tradeGeneratorActor() {
		return actorSystem.actorOf(TradeGeneratorActor.props(zerodhaProperties, kiteTradeFacade)
				.withRouter(new SmallestMailboxPool(zerodhaProperties.getOrderActorRoutees())));
	}
	
	@Bean(name=BEAN_AKKA_TRADEABLE_TICK_DATA_LOGGING_ACTOR_CANCELLABLE)
	public Cancellable tradeableTickDataLoggingActorCancellable() {
		return actorSystem.scheduler()
				.schedule(Duration.create(zerodhaProperties.getTradeableTickDataLoggingInitDelay(), TimeUnit.MILLISECONDS)
						, Duration.create(zerodhaProperties.getTradeableTickDataLoggingInterval(), TimeUnit.MILLISECONDS)
						, actorSystem.actorOf(TradeableTickDataLoggingActor.props(zerodhaProperties))
						, ACTOR_TRADEABLE_TICK_DATA_LOGGING_ACTOR_MSG_TYPE_LOG_TICK_DATA
						, actorSystem.dispatcher()
						, ActorRef.noSender());
	}
	
}
