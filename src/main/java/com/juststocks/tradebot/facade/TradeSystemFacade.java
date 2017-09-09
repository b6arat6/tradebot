/**
 * 
 */
package com.juststocks.tradebot.facade;

import java.util.ArrayList;
import java.util.Map;

import com.juststocks.tradebot.constants.KiteOrderParamValuesEnum;
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
	
	Map<String, Object> buildOrderParamMap(
			KiteOrderParamValuesEnum exchange,
			String tradingsymbol,
			KiteOrderParamValuesEnum transaction_type,
			int quantity,
			double price,
			KiteOrderParamValuesEnum product,
			KiteOrderParamValuesEnum order_type,
			KiteOrderParamValuesEnum validity,
			int disclosed_quantity,
			double trigger_price,
			double squareoff_value,
			double stoploss_value,
			double trailing_stoploss);
	
}
