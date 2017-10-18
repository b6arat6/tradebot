/**
 * 
 */
package com.juststocks.tradebot.actor;

import static com.juststocks.tradebot.bean.ZerodhaProperties.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.ZerodhaProperties;
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
	
	private ZerodhaProperties zerodhaProperties;
	
	public static Props props(ZerodhaProperties zerodhaProperties) {
		return Props.create(TradeableTickDataLoggingActor.class, () -> new TradeableTickDataLoggingActor(zerodhaProperties));
	}
	
	public TradeableTickDataLoggingActor(ZerodhaProperties zerodhaProperties) {
		this.zerodhaProperties = zerodhaProperties;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, message -> {
			Tick tick;
			SortedSet<OLTick> olTickSortedSet = new TreeSet<>(olTickMap.values());
			LOGGER.info(OL_TICK_MAP_SIZE, olTickSortedSet.size());
			for (OLTick olTick : olTickSortedSet) {
				tick = olTick.tick;
				LOGGER.info(OHL_OL_TICK, zerodhaProperties.getTradingInstrumentMap().get(olTick.tick.getToken()).getTradingsymbol(), tick.getLastTradedPrice(),
						tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), olTick.getTotalNetLowHighChange(), olTick.getNetLowChange(),
						olTick.getNetHighChange(), olTick.isTbGreaterThanTs(), tick.getToken());
			}
			SortedSet<OHTick> ohTickSortedSet = new TreeSet<>(ohTickMap.values());
			LOGGER.info(OH_TICK_MAP_SIZE, ohTickSortedSet.size());
			for (OHTick ohTick : ohTickSortedSet) {
				tick = ohTick.tick;
				LOGGER.info(OHL_OH_TICK, zerodhaProperties.getTradingInstrumentMap().get(ohTick.tick.getToken()).getTradingsymbol(), tick.getLastTradedPrice(),
						tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), ohTick.getTotalNetLowHighChange(), ohTick.getNetLowChange(),
						ohTick.getNetHighChange(), !ohTick.isTbGreaterThanTs(), tick.getToken());
			}
			LOGGER.info(ORDERED_TICK_MAP_SIZE, orderedTickMap.size());
			for (Long token : orderedTickMap.keySet()) {
				LOGGER.info(ORDERED_TICK, zerodhaProperties.getTradingInstrumentMap().get(token).getTradingsymbol());
			}
			LOGGER.info(NON_OHL_TICK_SET_SIZE, nonOHLTickSet.size());
			LOGGER.info(TOTAL_TICK_SET_SIZE, olTickSortedSet.size() + ohTickSortedSet.size() + nonOHLTickSet.size() + orderedTickMap.size());
		}).build();
	}

}
