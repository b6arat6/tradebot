/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteProperties.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.bean.OLTick;
import com.juststocks.tradebot.constants.KiteOrderParamValuesEnum;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.facade.KiteTradeSystemFacade;
import com.rainmatter.kitehttp.exceptions.KiteException;
import com.rainmatter.models.IndicesQuote;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class OrderActor extends AbstractActor implements TradebotConstants {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_ORDER);
	
	public static Props props(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		return Props.create(OrderActor.class, () -> new OrderActor(kiteProperties, kiteTradeSystemFacade));
	}
	
	private KiteProperties kiteProperties;
	
	private KiteTradeSystemFacade kiteTradeSystemFacade;	
	
	public OrderActor(KiteProperties kiteProperties, KiteTradeSystemFacade kiteTradeSystemFacade) {
		this.kiteProperties = kiteProperties;
		this.kiteTradeSystemFacade = kiteTradeSystemFacade;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, orderType -> {
			if (orderType.equals(ACTOR_ORDER_MSG_TYPE_OHL_STRATEGY)) {
				LOGGER.info(TRADE_TYPE_OHL_STRATEGY_TRIGGERED);
				int olTradeCount = 0;
				IndicesQuote indicesQuote;
				for (OLTick olTick : olTickMap.values()) {
					if (olTick.isOL()) {
						indicesQuote = kiteTradeSystemFacade.<IndicesQuote> getQuoteIndices(olTick.tick.getToken());
						olTick.setLastTradedPrice(indicesQuote.lastPrice);
						if (olTick.getNetLowChange() < kiteProperties.getOhlTradeableNLC()
								&& olTick.getNetHighChange() < kiteProperties.getOhlTradeableNHC()
								&& (!kiteProperties.isEnableOHLTsTbConstraint()) || olTick.isTbGreaterThanTs()) {
							try {
								kiteTradeSystemFacade.getKiteConnect().placeOrder(
										kiteTradeSystemFacade.buildOrderParamMap(
												KiteOrderParamValuesEnum.EXCHANGE_NSE
												, kiteProperties.getTradingSymbolMap().get(olTick.tick.getToken())
												, KiteOrderParamValuesEnum.TRANSACTION_TYPE_BUY
												, 1
												, indicesQuote.lastPrice + 1
												, KiteOrderParamValuesEnum.PRODUCT_MIS
												, KiteOrderParamValuesEnum.ORDER_TYPE_NRML
												, KiteOrderParamValuesEnum.VALIDITY_IOC
												, -1
												, -1
												, -1
												, -1
												, -1)
								, KiteOrderParamValuesEnum.VARIETY_REGULAR.toString());
								olTradeCount++;
								if (olTradeCount <= kiteProperties.getOhlTradeCount()) {
									break;
								}
							} catch (KiteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}).build();
	}

}
