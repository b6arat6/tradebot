/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteConnectProperties.ohTickMap;
import static com.juststocks.tradebot.bean.KiteConnectProperties.olTickMap;

import java.util.ArrayList;
import java.util.List;

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
public final class OHLTradeStrategyActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OHLTradeStrategyActor.class);
	
	public static Props props(KiteConnectProperties kiteConnectProperties, KiteConnectClientFacade kiteConnectClientFacade) {
		return Props.create(OHLTradeStrategyActor.class, () -> new OHLTradeStrategyActor(kiteConnectProperties, kiteConnectClientFacade));
	}

	private KiteConnectProperties kiteConnectProperties;
	
	private KiteConnectClientFacade kiteConnectClientFacade;
	
	public OHLTradeStrategyActor(KiteConnectProperties kiteConnectProperties, KiteConnectClientFacade kiteConnectClientFacade) {
		this.kiteConnectProperties = kiteConnectProperties;
		this.kiteConnectClientFacade = kiteConnectClientFacade;
	}	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MyArrayList.class, myArrayList -> {
			List<Long> unsubscribeTicks = new ArrayList<>(); 
			for (Tick tick : myArrayList.ticks) {
				if (tick.getOpenPrice() == tick.getLowPrice()) {
					olTickMap.put(tick.getToken(), tick);
					LOGGER.info(TRADE_STRATEGY_OHL_OL
							, kiteConnectProperties.getTokenMap().get(tick.getToken())
							, tick.getLowPrice()
							, tick.getOpenPrice()
							, tick.getHighPrice()
							, tick.getLastTradedPrice());
				} else if (tick.getOpenPrice() == tick.getHighPrice()) {
					ohTickMap.put(tick.getToken(), tick);
					LOGGER.info(TRADE_STRATEGY_OHL_OH
							, kiteConnectProperties.getTokenMap().get(tick.getToken())
							, tick.getLowPrice()
							, tick.getOpenPrice()
							, tick.getHighPrice()
							, tick.getLastTradedPrice());
				} else {
					if (olTickMap.containsKey(tick.getToken())) {
						olTickMap.remove(tick.getToken());
						LOGGER.info(TRADE_STRATEGY_OHL_OL_REMOVED, kiteConnectProperties.getTokenMap().get(tick.getToken()));
					}
					if (ohTickMap.containsKey(tick.getToken())) {
						LOGGER.info(TRADE_STRATEGY_OHL_OH_REMOVED, kiteConnectProperties.getTokenMap().get(tick.getToken()));
						ohTickMap.remove(tick.getToken());
					}
 					unsubscribeTicks.add(tick.getToken());
				}
			}
			if (unsubscribeTicks.size() > 0) {
				kiteConnectClientFacade.unsubscribeInstruments((ArrayList<Long>) unsubscribeTicks);
			}
		}).build();
	}
	
	public static class OLTick extends Tick implements Comparable<OLTick> {
		Tick tick;
		public OLTick(Tick tick) {
			this.tick = tick;
		}
		
		@Override
		public int compareTo(OLTick olTick) {
			Tick otherTick = olTick.tick;
			
			double thisTickNetLowChange = (tick.getLastTradedPrice() - tick.getLowPrice())/tick.getLowPrice();
			double otherTickNetLowChange = (otherTick.getLastTradedPrice() - otherTick.getLowPrice())/otherTick.getLowPrice();
			
			double thisTickNetHighChange = (tick.getHighPrice() - tick.getLastTradedPrice())/tick.getHighPrice();
			double otherTickNetHighChange = (otherTick.getHighPrice() - otherTick.getLastTradedPrice())/otherTick.getHighPrice();
			
			boolean thisTickTbTsState = tick.getTotalBuyQuantity() > tick.getTotalSellQuantity();
			boolean otherTickTbTsState = otherTick.getTotalBuyQuantity() > otherTick.getTotalSellQuantity();
			
			if (thisTickNetLowChange < otherTickNetLowChange
					&& thisTickNetHighChange < otherTickNetHighChange
//					&& thisTickTbTsState
					) {
				return -1;
			} else if (thisTickNetLowChange > otherTickNetLowChange
					&& thisTickNetHighChange > otherTickNetHighChange
//					&& otherTickTbTsState
					) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	public static class OHTick extends Tick implements Comparable<OHTick> {
		Tick tick;
		public OHTick(Tick tick) {
			this.tick = tick;
		}
		
		@Override
		public int compareTo(OHTick ohTick) {
			Tick otherTick = ohTick;
			
			double thisTickNetLowChange = (tick.getLastTradedPrice() - tick.getLowPrice())/tick.getLowPrice();
			double otherTickNetLowChange = (otherTick.getLastTradedPrice() - otherTick.getLowPrice())/otherTick.getLowPrice();
			
			double thisTickNetHighChange = (tick.getHighPrice() - tick.getLastTradedPrice())/tick.getHighPrice();
			double otherTickNetHighChange = (otherTick.getHighPrice() - otherTick.getLastTradedPrice())/otherTick.getHighPrice();
			
			boolean thisTickTbTsState = tick.getTotalBuyQuantity() < tick.getTotalSellQuantity();
			boolean otherTickTbTsState = otherTick.getTotalBuyQuantity() < otherTick.getTotalSellQuantity();
			
			if (thisTickNetLowChange < otherTickNetLowChange
					&& thisTickNetHighChange < otherTickNetHighChange
//					&& thisTickTbTsState
					) {
				return -1;
			} else if (thisTickNetLowChange > otherTickNetLowChange
					&& thisTickNetHighChange > otherTickNetHighChange
//					&& otherTickTbTsState
					) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	public static class MyArrayList extends ArrayList<Tick> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6709048444526628085L;
		
		private ArrayList<Tick> ticks;
		
		public MyArrayList(ArrayList<Tick> ticks) {
			this.ticks = ticks;
		}
	}

}
