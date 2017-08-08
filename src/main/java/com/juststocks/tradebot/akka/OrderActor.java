/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteConnectProperties.ohTickMap;
import static com.juststocks.tradebot.bean.KiteConnectProperties.olTickMap;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteConnectClientFacade;
import com.rainmatter.models.Tick;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class OrderActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderActor.class);
	
	public static Props props(KiteConnectProperties kiteConnectProperties, KiteConnectClientFacade kiteConnectClientFacade) {
		return Props.create(OrderActor.class, () -> new OrderActor(kiteConnectProperties, kiteConnectClientFacade));
	}
	
	private KiteConnectProperties kiteConnectProperties;
	
	private KiteConnectClientFacade kiteConnectClientFacade;	
	
	public OrderActor(KiteConnectProperties kiteConnectProperties, KiteConnectClientFacade kiteConnectClientFacade) {
		this.kiteConnectProperties = kiteConnectProperties;
		this.kiteConnectClientFacade = kiteConnectClientFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, orderType -> {
			if (orderType.equals(ORDER_TYPE_OHL_TRADE_STRATEGY)) {
				for (Entry<Long, Tick> entry : olTickMap.entrySet()) {
					
//					kiteConnectClientFacade.getKiteConnect().placeOrder(params, variety);
				}
			}
		}).build();
	}

}
