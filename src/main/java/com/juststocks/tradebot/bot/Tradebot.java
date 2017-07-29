/**
 * 
 */
package com.juststocks.tradebot.bot;

import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
public interface Tradebot extends TradebotConstants {
	
	boolean run(String[] args);
	
	boolean init(String[] args);

	boolean service();
	
}
