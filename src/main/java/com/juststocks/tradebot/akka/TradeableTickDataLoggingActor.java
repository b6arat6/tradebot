/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteProperties.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.bean.OHTick;
import com.juststocks.tradebot.bean.OLTick;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.rainmatter.models.Tick;

import akka.actor.AbstractActor;
import akka.actor.Props;

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
			SortedSet<OLTick> olTickSortedSet = new TreeSet<>(olTickMap.values());
			LOGGER.info(OL_TICK_SET_SIZE, olTickSortedSet.size());
			for (OLTick olTick : olTickSortedSet) {
				tick = olTick.tick;
				LOGGER.info(OHL_OL_TICK, kiteProperties.getTradingInstrumentMap().get(olTick.tick.getToken()).getTradingsymbol(), tick.getLastTradedPrice(),
						tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), olTick.getTotalNetLowHighChange(), olTick.getNetLowChange(),
						olTick.getNetHighChange(), olTick.isTbGreaterThanTs(), tick.getToken());
			}
			SortedSet<OHTick> ohTickSortedSet = new TreeSet<>(ohTickMap.values());
			LOGGER.info(OH_TICK_SET_SIZE, ohTickSortedSet.size());
			for (OHTick ohTick : ohTickSortedSet) {
				tick = ohTick.tick;
				LOGGER.info(OHL_OH_TICK, kiteProperties.getTradingInstrumentMap().get(ohTick.tick.getToken()).getTradingsymbol(), tick.getLastTradedPrice(),
						tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), ohTick.getTotalNetLowHighChange(), ohTick.getNetLowChange(),
						ohTick.getNetHighChange(), !ohTick.isTbGreaterThanTs(), tick.getToken());
			}
			LOGGER.info(NON_OHL_TICK_SET_SIZE, nonOHLTickSet.size());
			LOGGER.info(OL_OH_NON_OHL_TICK_SET_SIZE, olTickSortedSet.size() + ohTickSortedSet.size() + nonOHLTickSet.size());
		}).build();
	}

}
