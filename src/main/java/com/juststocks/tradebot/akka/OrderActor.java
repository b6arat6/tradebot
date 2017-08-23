/**
 * 
 */
package com.juststocks.tradebot.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteConnectTradeSystemFacade;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class OrderActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderActor.class);
	
	public static Props props(KiteConnectProperties kiteConnectProperties, KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade) {
		return Props.create(OrderActor.class, () -> new OrderActor(kiteConnectProperties, kiteConnectTradeSystemFacade));
	}
	
	private KiteConnectProperties kiteConnectProperties;
	
	private KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade;	
	
	public OrderActor(KiteConnectProperties kiteConnectProperties, KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade) {
		this.kiteConnectProperties = kiteConnectProperties;
		this.kiteConnectTradeSystemFacade = kiteConnectTradeSystemFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, orderType -> {
			if (orderType.equals(ORDER_TYPE_OHL_STRATEGY)) {
				LOGGER.info(LOG_ORDER_TYPE_OHL_STRATEGY_ORDERS_TRIGGERED);
//				kiteConnectTradeSystemFacade.getKiteConnect().placeOrder(params, variety);
			}
		}).build();
	}

}
