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
import com.juststocks.tradebot.bean.OHLTick;
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
			OHLTick ohlTick = new OHLTick(tick);
			if (!nonOHLTickSet.contains(ohlTick)) {
				if (tick.getOpenPrice() == tick.getLowPrice()) {
					if (olTickSet.contains(olTick) && olTickSet.remove(olTick)) {
						LOGGER.info(STRATEGY_OHL_OL_UPDATED, kiteProperties.getTokenMap().get(tick.getToken()),
								tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
								tick.getLastTradedPrice());
						olTickSet.add(olTick);
					} else {
						LOGGER.info(STRATEGY_OHL_OL, kiteProperties.getTokenMap().get(tick.getToken()),
								tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
								tick.getLastTradedPrice());
						olTickSet.add(olTick);
					}
				} else if (tick.getOpenPrice() == tick.getHighPrice()) {
					if (ohTickSet.contains(ohTick) && ohTickSet.remove(ohTick)) {
						LOGGER.info(STRATEGY_OHL_OH_UPDATED, kiteProperties.getTokenMap().get(tick.getToken()),
								tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
								tick.getLastTradedPrice());
						ohTickSet.add(ohTick);
					} else {
						LOGGER.info(STRATEGY_OHL_OH, kiteProperties.getTokenMap().get(tick.getToken()),
								tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
								tick.getLastTradedPrice());
						ohTickSet.add(ohTick);
					}
				} else {
					if (olTickSet.contains(olTick) && olTickSet.remove(olTick)) {
						LOGGER.warn(STRATEGY_OHL_OL_REMOVED, kiteProperties.getTokenMap().get(tick.getToken()));
					} else if (ohTickSet.contains(ohTick) && ohTickSet.remove(ohTick)) {
						LOGGER.warn(STRATEGY_OHL_OH_REMOVED, kiteProperties.getTokenMap().get(tick.getToken()));
					}
					LOGGER.info(LOG_INSTRUMENT_UNSUBSCRIBING, kiteProperties.getTokenMap().get(tick.getToken()));
					nonOHLTickSet.add(ohlTick);
					unsubscribeTicks.add(tick.getToken());
				}
				if (unsubscribeTicks.size() > 0) {
					kiteTradeSystemFacade.unsubscribeInstruments((ArrayList<Long>) unsubscribeTicks);
				}
				LOGGER.info(OL_TICK_SET_SIZE, KiteProperties.olTickSet.size());
				LOGGER.info(OH_TICK_SET_SIZE, KiteProperties.ohTickSet.size());
				LOGGER.info(NON_OHL_TICK_SET_SIZE, KiteProperties.nonOHLTickSet.size());
			}
		}).build();
	}

}
