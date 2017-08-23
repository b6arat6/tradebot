/**
 * 
 */
package com.juststocks.tradebot.facade;

import java.util.ArrayList;

import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
public interface TradeSystemFacade extends TradebotConstants {

	boolean login();
	
	boolean authenticate();
	
	boolean loadParameters();
	
	boolean getInstruments();

	boolean getInstruments(String exchange);
	
	boolean initWebSocket();
	
	public void triggerOHLStrategyOrders();

	boolean subscribeInstruments(ArrayList<Long> tokens);
	
	boolean unsubscribeInstruments(ArrayList<Long> tokens);
	
}
