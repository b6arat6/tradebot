/**
 * 
 */
package com.juststocks.tradebot.util;

/**
 * @author bharath_kandasamy
 *
 */
public interface HttpUtil<T> {

	T executeGet(String uri);
	
	T executePost();
	
}
