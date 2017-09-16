/**
 * 
 */
package com.juststocks.tradebot.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.exception.AuthException;

/**
 * @author bharath_kandasamy
 *
 */
public interface TradeSystemFacade extends TradebotConstants {

	boolean login();
	
	boolean authenticate() throws AuthException;
	
	boolean loadParameters();
	
	boolean getInstruments();

	boolean getInstruments(String exchange);
	
	<T> T getQuoteIndices(Long token);
	
	boolean initWebSocket();
	
	public void triggerOHLStrategyOrders();

	boolean subscribeInstruments(ArrayList<Long> tokens);
	
	boolean unsubscribeInstruments(ArrayList<Long> tokens);
	
	Map<String, Object> buildOrderParamMap(String exchange,
			String tradingsymbol,
			String transactionType,
			int quantity,
			double price,
			String product,
			String orderType,
			String validity,
			int disclosedQuantity,
			double triggerPrice,
			double squareoffValue,
			double stoplossValue,
			double trailingStoploss);
	
	<E> boolean placeOHLOrder(Collection<E> ohlTickCollection, final int tradeCount, final ParameterData.ValueIndexEnum transactionType, int ohlSign);
	
}
