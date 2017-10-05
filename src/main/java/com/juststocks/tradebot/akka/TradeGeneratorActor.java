/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteProperties.*;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.OHLStrategyEnum;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeSystemFacade;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class TradeGeneratorActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_ORDER);
	
	public static Props props(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		return Props.create(TradeGeneratorActor.class, () -> new TradeGeneratorActor(kiteProperties, kiteTradeSystemFacade));
	}
	
	private KiteProperties kiteProperties;
	
	private KiteTradeSystemFacade kiteTradeSystemFacade;
	
	public TradeGeneratorActor(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		this.kiteProperties = kiteProperties;
		this.kiteTradeSystemFacade = kiteTradeSystemFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, tradeType -> {
			if (tradeType.equals(ACTOR_TRADE_GENERATOR_MSG_TYPE_TRADE_OHL)) {
				LOGGER.info(TRADE_TYPE_OHL_STRATEGY_TRIGGERED);
				if (kiteProperties.getOhlStrategyMode() == Integer.valueOf(OHLStrategyEnum.MODE_OHL.getValue())
						|| kiteProperties.getOhlStrategyMode() == Integer.valueOf(OHLStrategyEnum.MODE_OL.getValue())) {
					kiteTradeSystemFacade.placeOHLOrder(
							new TreeSet<>(olTickMap.values())
							, kiteProperties.getOhlOLTradeCount()
							, ParameterData.ValueIndexEnum.TRANSACTION_TYPE_BUY
							, 1);
				}
				if (kiteProperties.getOhlStrategyMode() == Integer.valueOf(OHLStrategyEnum.MODE_OHL.getValue())
						|| kiteProperties.getOhlStrategyMode() == Integer.valueOf(OHLStrategyEnum.MODE_OH.getValue())) {
					kiteTradeSystemFacade.placeOHLOrder(
							new TreeSet<>(ohTickMap.values())
							, kiteProperties.getOhlOHTradeCount()
							, ParameterData.ValueIndexEnum.TRANSACTION_TYPE_SELL
							, -1);
				}
			}
		}).build();
	}
}
