/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteProperties.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.bean.OHTick;
import com.juststocks.tradebot.bean.OLTick;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeSystemFacade;
import com.rainmatter.models.Tick;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public final class OHLStrategyActor extends AbstractActor implements TradebotConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_STRATEGY);

	public static Props props(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		return Props.create(OHLStrategyActor.class, () -> new OHLStrategyActor(kiteProperties, kiteTradeSystemFacade));
	}

	private KiteProperties kiteProperties;

	private KiteTradeSystemFacade kiteTradeSystemFacade;

	public OHLStrategyActor(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		this.kiteProperties = kiteProperties;
		this.kiteTradeSystemFacade = kiteTradeSystemFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Tick.class, tick -> {
			List<Long> unsubscribeTicks = new ArrayList<>();
			OLTick olTick = new OLTick(tick);
			OHTick ohTick = new OHTick(tick);

			if (olTick.isOL()) {
				if (olTickMap.containsKey(olTick) && olTickMap.put(olTick, tick.getToken()) != null) {
					LOGGER.info(STRATEGY_OHL_OL_UPDATED, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(),
							tick.getLastTradedPrice(), 
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
							tick.getToken());
				} else {
					LOGGER.info(STRATEGY_OHL_OL, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(),
							tick.getLastTradedPrice(), 
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
							tick.getToken());
					olTickMap.put(olTick, tick.getToken());
				}
			} else if (ohTick.isOH()) {
				if (ohTickMap.containsKey(ohTick) && ohTickMap.put(ohTick, tick.getToken()) != null) {
					LOGGER.info(STRATEGY_OHL_OH_UPDATED, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(),
							tick.getLastTradedPrice(),   
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
							tick.getToken());
				} else {
					LOGGER.info(STRATEGY_OHL_OH, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(),
							tick.getLastTradedPrice(), 
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
							tick.getToken());
					ohTickMap.put(ohTick, tick.getToken());
				}
			} else {
				if (olTickMap.containsKey(olTick) && olTickMap.remove(olTick) != null) {
					LOGGER.warn(STRATEGY_OHL_OL_REMOVED, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(), tick.getToken(),
							tick.getLastTradedPrice(), 
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
							tick.getToken());
				} else if (ohTickMap.containsKey(ohTick) && ohTickMap.remove(ohTick) != null) {
					LOGGER.warn(STRATEGY_OHL_OH_REMOVED, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(), tick.getToken(),
							tick.getLastTradedPrice(), 
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
							tick.getToken());
				}
				LOGGER.info(INSTRUMENT_UNSUBSCRIBING, kiteProperties.getTradingInstrumentMap().get(tick.getToken()).getTradingsymbol(), tick.getToken());
				nonOHLTickSet.add(tick.getToken());
				unsubscribeTicks.add(tick.getToken());
			}
			if (unsubscribeTicks.size() > 0) {
				kiteTradeSystemFacade.unsubscribeInstruments((ArrayList<Long>) unsubscribeTicks);
			}
			LOGGER.info(OL_TICK_MAP_SIZE, KiteProperties.olTickMap.size());
			LOGGER.info(OH_TICK_MAP_SIZE, KiteProperties.ohTickMap.size());
			LOGGER.info(NON_OHL_TICK_SET_SIZE, KiteProperties.nonOHLTickSet.size());
		}).build();
	}

}
