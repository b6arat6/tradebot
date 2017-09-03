/**
 * 
 */
package com.juststocks.tradebot.akka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.bean.OHTick;
import com.juststocks.tradebot.bean.OLTick;
import com.juststocks.tradebot.constants.TradebotConstants;

import akka.actor.AbstractActor;
import akka.actor.Props;

import static com.juststocks.tradebot.bean.KiteProperties.*;

/**
 * @author bharath_kandasamy
 *
 */
public class TradeableTickDataLoggingActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_TRADEABLE_TICK);
	
	private KiteProperties kiteProperties;
	
	public static Props props(KiteProperties kiteProperties) {
		return Props.create(TradeableTickDataLoggingActor.class, () -> new TradeableTickDataLoggingActor(kiteProperties));
	}
	
	public TradeableTickDataLoggingActor(KiteProperties kiteProperties) {
		this.kiteProperties = kiteProperties;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, message -> {
			LOGGER.info(OL_TICK_SET_SIZE, olTickSet.size());
			for (OLTick tick : olTickSet) {
				LOGGER.info(OHL_OL_TICK, kiteProperties.getTokenMap().get(tick.getToken()), tick.getLowPrice(),
						tick.getOpenPrice(), tick.getHighPrice(), tick.getLastTradedPrice(), tick.getNetLowChange(),
						tick.getNetHighChange(), tick.isTbGreaterThanTs(), tick.getToken());
			}
			LOGGER.info(OH_TICK_SET_SIZE, ohTickSet.size());
			for (OHTick tick : ohTickSet) {
				LOGGER.info(OHL_OH_TICK, kiteProperties.getTokenMap().get(tick.getToken()), tick.getLowPrice(),
						tick.getOpenPrice(), tick.getHighPrice(), tick.getLastTradedPrice(), tick.getNetLowChange(),
						tick.getNetHighChange(), tick.isTbGreaterThanTs(), tick.getToken());
			}
		}).build();
	}

}
