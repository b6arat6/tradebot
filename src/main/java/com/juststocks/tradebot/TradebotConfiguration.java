/**
 * 
 */
package com.juststocks.tradebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.juststocks.tradebot.akka.OHLStrategyActor;
import com.juststocks.tradebot.akka.OrderActor;
import com.juststocks.tradebot.akka.TickDispenserActor;
import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteConnectTradeSystemFacade;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootConfiguration
@EnableScheduling
@EnableConfigurationProperties(KiteConnectProperties.class)
public class TradebotConfiguration implements TradebotConstants {
	
	@Autowired
	public KiteConnectProperties kiteConnectProperties;
	
	@Autowired
	private KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade;
	
	@Autowired
	@Qualifier(AKKA_OHL_STRATEGY_ACTOR_REF)
	private ActorRef ohlTradeStrategyActorRef;
	
	@Bean(name=AKKA_ACTOR_SYSTEM)
	public ActorSystem actorSystem() {
		return ActorSystem.create(AKKA_ACTOR_SYSTEM);
	}
	
	@Bean(name=AKKA_OHL_STRATEGY_ACTOR_REF)
	public ActorRef ohlStrategyActor() {
		return actorSystem().actorOf(OHLStrategyActor.props(kiteConnectProperties, kiteConnectTradeSystemFacade));
	}
	
	@Bean(name=AKKA_TICK_DISPENSER_ACTOR_REF)
	public ActorRef tickDispenserActor() {
		return actorSystem().actorOf(TickDispenserActor.props(kiteConnectProperties, ohlTradeStrategyActorRef));
	}
	
	@Bean(name=AKKA_ORDER_ACTOR_REF)
	public ActorRef orderActor() {
		return actorSystem().actorOf(OrderActor.props(kiteConnectProperties, kiteConnectTradeSystemFacade));
	}
	
}
