/**
 * 
 */
package com.juststocks.tradebot.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeSystemFacade;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class OrderActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_ORDER);
	
	public static Props props(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		return Props.create(OrderActor.class, () -> new OrderActor(kiteProperties, kiteTradeSystemFacade));
	}
	
	private KiteProperties kiteProperties;
	
	private KiteTradeSystemFacade kiteTradeSystemFacade;	
	
	public OrderActor(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		this.kiteProperties = kiteProperties;
		this.kiteTradeSystemFacade = kiteTradeSystemFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, orderType -> {
			if (orderType.equals(ORDER_TYPE_OHL_STRATEGY)) {
				LOGGER.info(LOG_ORDER_TYPE_OHL_STRATEGY_ORDERS_TRIGGERED);
//				kiteTradeSystemFacade.getKiteConnect().placeOrder(params, variety);
			}
		}).build();
	}

}
