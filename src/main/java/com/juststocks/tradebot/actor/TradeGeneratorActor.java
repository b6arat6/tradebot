/**
 * 
 */
package com.juststocks.tradebot.actor;

import static com.juststocks.tradebot.bean.ZerodhaProperties.*;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.juststocks.tradebot.bean.ZerodhaProperties;
import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.TradeTriggerEnum;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeFacade;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class TradeGeneratorActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_ORDER);
	
	public static Props props(ZerodhaProperties zerodhaProperties, KiteTradeFacade kiteTradeFacade) {
		return Props.create(TradeGeneratorActor.class, () -> new TradeGeneratorActor(zerodhaProperties, kiteTradeFacade));
	}
	
	@SuppressWarnings("unused")
	private ZerodhaProperties zerodhaProperties;
	
	private KiteTradeFacade kiteTradeFacade;
	
	public TradeGeneratorActor(ZerodhaProperties zerodhaProperties, KiteTradeFacade kiteTradeFacade) {
		this.zerodhaProperties = zerodhaProperties;
		this.kiteTradeFacade = kiteTradeFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(TradeTriggerData.class, tradeTriggerData -> {
			if (tradeTriggerData.getTradeStrategy().equals(TradeTriggerEnum.TRADE_STRATEGY_1.getValue())) {
				LOGGER.info(TRADE_TYPE_OHL_STRATEGY_TRIGGERED);
				if (tradeTriggerData.getTradeStrategyType().equals(TradeTriggerEnum.TRADE_STRATEGY_TYPE_1.getValue())
						|| tradeTriggerData.getTradeStrategyType().equals(TradeTriggerEnum.TRADE_STRATEGY_TYPE_3.getValue())) {
					kiteTradeFacade.placeOHLOrder(
							new TreeSet<>(olTickMap.values())
							, tradeTriggerData.getTradeCount()
							, tradeTriggerData.getTradeQty()
							, ParameterData.ValueIndexEnum.TRANSACTION_TYPE_BUY
							, 1);
				}
				if (tradeTriggerData.getTradeStrategyType().equals(TradeTriggerEnum.TRADE_STRATEGY_TYPE_2.getValue())
						|| tradeTriggerData.getTradeStrategyType().equals(TradeTriggerEnum.TRADE_STRATEGY_TYPE_3.getValue())) {
					kiteTradeFacade.placeOHLOrder(
							new TreeSet<>(ohTickMap.values())
							, tradeTriggerData.getTradeCount()
							, tradeTriggerData.getTradeQty()
							, ParameterData.ValueIndexEnum.TRANSACTION_TYPE_SELL
							, -1);
				}
			}
		}).build();
	}

	//  1-OHL
	//  1-OL, 2-OH, 3-HL
	@Component
	public static class TradeTriggerData {
		String tradeStrategy;
		String tradeStrategyType;
		int tradeCount;
		int tradeQty;
		
		public String getTradeStrategy() {
			return tradeStrategy;
		}
		public void setTradeStrategy(String tradeStrategy) {
			this.tradeStrategy = tradeStrategy;
		}
		public String getTradeStrategyType() {
			return tradeStrategyType;
		}
		public void setTradeStrategyType(String tradeStrategyType) {
			this.tradeStrategyType = tradeStrategyType;
		}
		public int getTradeCount() {
			return tradeCount;
		}
		public void setTradeCount(int tradeCount) {
			this.tradeCount = tradeCount;
		}
		public int getTradeQty() {
			return tradeQty;
		}
		public void setTradeQty(int tradeQty) {
			this.tradeQty = tradeQty;
		}
	}
	
}
