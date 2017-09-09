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

import com.juststocks.tradebot.akka.OHLStrategyActor;
import com.juststocks.tradebot.akka.OrderActor;
import com.juststocks.tradebot.akka.TickDispenserActor;
import com.juststocks.tradebot.akka.TradeableTickDataLoggingActor;
import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeSystemFacade;

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
@EnableConfigurationProperties(KiteProperties.class)
public class TradebotConfiguration implements TradebotConstants {
	
	@Autowired
	public KiteProperties kiteProperties;
	
	@Autowired
	private KiteTradeSystemFacade kiteTradeSystemFacade;
	
	@Autowired
	private ActorSystem actorSystem;
	
	@Autowired
	@Qualifier(AKKA_OHL_STRATEGY_ACTOR_REF)
	private ActorRef ohlTradeStrategyActorRef;
	
	@Bean(name=AKKA_ACTOR_SYSTEM)
	public ActorSystem actorSystem() {
		return ActorSystem.create(AKKA_ACTOR_SYSTEM);
	}
	
	@Bean(name=AKKA_OHL_STRATEGY_ACTOR_REF)
	public ActorRef ohlStrategyActor() {
		return actorSystem.actorOf(OHLStrategyActor.props(kiteProperties, kiteTradeSystemFacade)
				.withRouter(new SmallestMailboxPool(kiteProperties.getOhlStrategyActorRoutees())));
	}
	
	@Bean(name=AKKA_TICK_DISPENSER_ACTOR_REF)
	public ActorRef tickDispenserActor() {
		return actorSystem.actorOf(TickDispenserActor.props(kiteProperties, ohlTradeStrategyActorRef)
				.withRouter(new SmallestMailboxPool(kiteProperties.getTickDisperserActorRoutees())));
	}
	
	@Bean(name=AKKA_ORDER_ACTOR_REF)
	public ActorRef orderActor() {
		return actorSystem.actorOf(OrderActor.props(kiteProperties, kiteTradeSystemFacade)
				.withRouter(new SmallestMailboxPool(kiteProperties.getOrderActorRoutees())));
	}
	
	@Bean(name=AKKA_TRADEABLE_TICK_DATA_LOGGING_ACTOR_CANCELLABLE)
	public Cancellable tradeableTickDataLoggingActorCancellable() {
		return actorSystem.scheduler()
				.schedule(Duration.create(kiteProperties.getTradeableTickDataLoggingInitDelay(), TimeUnit.MILLISECONDS)
						, Duration.create(kiteProperties.getTradeableTickDataLoggingInterval(), TimeUnit.MILLISECONDS)
						, actorSystem.actorOf(TradeableTickDataLoggingActor.props(kiteProperties))
						, ACTOR_TRADEABLE_MSG_TICK_DATA_LOGGING
						, actorSystem.dispatcher()
						, ActorRef.noSender());
	}
	
}
