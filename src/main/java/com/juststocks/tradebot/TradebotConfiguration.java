/**
 * 
 */
package com.juststocks.tradebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.juststocks.tradebot.akka.OHLTradeStrategyActor;
import com.juststocks.tradebot.akka.OrderActor;
import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteConnectClientFacade;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootConfiguration
@EnableConfigurationProperties(KiteConnectProperties.class)
public class TradebotConfiguration implements TradebotConstants {
	
	@Autowired
	public KiteConnectProperties kiteConnectProperties;
	
	@Autowired
	private KiteConnectClientFacade kiteConnectClientFacade;
	
	@Bean(name=AKKA_ACTOR_SYSTEM)
	public ActorSystem actorSystem() {
		return ActorSystem.create(AKKA_ACTOR_SYSTEM);
	}
	
	@Bean(name=AKKA_OHL_TRADE_STRATEGY_ACTOR)
	public ActorRef ohlTradeStrategyActor() {
		return actorSystem().actorOf(OHLTradeStrategyActor.props(kiteConnectProperties, kiteConnectClientFacade));
	}
	
	@Bean(name=AKKA_OHL_TRADE_STRATEGY_ACTOR)
	public ActorRef orderActor() {
		return actorSystem().actorOf(OrderActor.props(kiteConnectProperties, kiteConnectClientFacade));
	}
	
}
