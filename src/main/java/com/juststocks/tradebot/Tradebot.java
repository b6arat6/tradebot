/**
 * 
 */
package com.juststocks.tradebot;

import com.juststocks.tradebot.constants.TradebotConstants;
import com.juststocks.tradebot.exception.AuthException;

/**
 * @author bharath_kandasamy
 *
 */
public interface Tradebot extends TradebotConstants {
	
	boolean run(String[] args);
	
	boolean init(String[] args);

	boolean execute() throws AuthException;
	
	boolean shutdown();
	
}
