/**
 * 
 */
package com.juststocks.tradebot.facade;

import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
public interface GenericClientFacade extends TradebotConstants {

	boolean login();
	
	boolean authenticate();
	
	boolean loadParameters();
	
}
