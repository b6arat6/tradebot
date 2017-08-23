/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteConnectProperties.olTickSet;
import static com.juststocks.tradebot.bean.KiteConnectProperties.ohTickSet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.bean.OHTick;
import com.juststocks.tradebot.bean.OLTick;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteConnectTradeSystemFacade;
import com.rainmatter.models.Tick;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public final class OHLStrategyActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OHLStrategyActor.class);
	
	public static Props props(KiteConnectProperties kiteConnectProperties, KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade) {
		return Props.create(OHLStrategyActor.class, () -> new OHLStrategyActor(kiteConnectProperties, kiteConnectTradeSystemFacade));
	}

	private KiteConnectProperties kiteConnectProperties;
	
	private KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade;
	
	public OHLStrategyActor(KiteConnectProperties kiteConnectProperties, KiteConnectTradeSystemFacade kiteConnectTradeSystemFacade) {
		this.kiteConnectProperties = kiteConnectProperties;
		this.kiteConnectTradeSystemFacade = kiteConnectTradeSystemFacade;
	}	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Tick.class, tick -> {
			List<Long> unsubscribeTicks = new ArrayList<>();
			OLTick olTick = null;
			OHTick ohTick = null;
			olTick = new OLTick(tick);
			ohTick = new OHTick(tick);
			if (tick.getOpenPrice() == tick.getLowPrice()) {
				if (olTickSet.contains(olTick)) {
					olTickSet.remove(olTick);
					LOGGER.info(STRATEGY_OHL_OL_UPDATED, kiteConnectProperties.getTokenMap().get(tick.getToken()),
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), tick.getLastTradedPrice());
				} else {
					LOGGER.info(STRATEGY_OHL_OL, kiteConnectProperties.getTokenMap().get(tick.getToken()),
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), tick.getLastTradedPrice());
				}
				olTickSet.add(olTick);
			} else if (tick.getOpenPrice() == tick.getHighPrice()) {
				if (ohTickSet.contains(ohTick)) {
					ohTickSet.remove(ohTick);
					LOGGER.info(STRATEGY_OHL_OH_UPDATED, kiteConnectProperties.getTokenMap().get(tick.getToken()),
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), tick.getLastTradedPrice());
				} else {
					LOGGER.info(STRATEGY_OHL_OH, kiteConnectProperties.getTokenMap().get(tick.getToken()),
							tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(), tick.getLastTradedPrice());
				}
				ohTickSet.add(ohTick);
			} else {
				if (olTickSet.contains(olTick)) {
					if (olTickSet.remove(olTick)) {
						LOGGER.info(STRATEGY_OHL_OL_REMOVED, kiteConnectProperties.getTokenMap().get(tick.getToken()));
						LOGGER.info(STRATEGY_OHL_OL, kiteConnectProperties.getTokenMap().get(tick.getToken()),
								tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
								tick.getLastTradedPrice());
					}
				}
				if (ohTickSet.contains(ohTick)) {
					if (ohTickSet.remove(ohTick)) {
						LOGGER.info(STRATEGY_OHL_OH_REMOVED, kiteConnectProperties.getTokenMap().get(tick.getToken()));
						LOGGER.info(STRATEGY_OHL_OH, kiteConnectProperties.getTokenMap().get(tick.getToken()),
								tick.getLowPrice(), tick.getOpenPrice(), tick.getHighPrice(),
								tick.getLastTradedPrice());
					}
				}
				LOGGER.info(LOG_INSTRUMENT_UNSUBSCRIBING, kiteConnectProperties.getTokenMap().get(tick.getToken()));
				unsubscribeTicks.add(tick.getToken());
			}
			if (unsubscribeTicks.size() > 0) {
				kiteConnectTradeSystemFacade.unsubscribeInstruments((ArrayList<Long>) unsubscribeTicks);
			}
			LOGGER.info(OL_TICK_SET_SIZE, KiteConnectProperties.olTickSet.size());
			LOGGER.info(OH_TICK_SET_SIZE, KiteConnectProperties.ohTickSet.size());
		}).build();
	}
	
}
