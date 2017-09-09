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
import com.rainmatter.models.Tick;

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
			Tick tick;
			LOGGER.info(OL_TICK_MAP_SIZE, olTickMap.size());
			for (OLTick olTick : olTickMap.values()) {
				tick = olTick.tick;
				LOGGER.info(OHL_OL_TICK, kiteProperties.getTradingSymbolMap().get(tick.getToken()), tick.getLastTradedPrice(),
						tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), olTick.getNetLowChange(),
						olTick.getNetHighChange(), olTick.isTbGreaterThanTs(), tick.getToken());
			}
			LOGGER.info(OH_TICK_MAP_SIZE, ohTickMap.size());
			for (OHTick ohTick : ohTickMap.values()) {
				tick = ohTick.tick;
				LOGGER.info(OHL_OH_TICK, kiteProperties.getTradingSymbolMap().get(tick.getToken()), tick.getLastTradedPrice(),
						tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), ohTick.getNetLowChange(),
						ohTick.getNetHighChange(), !ohTick.isTbGreaterThanTs(), tick.getToken());
			}
		}).build();
	}

}
