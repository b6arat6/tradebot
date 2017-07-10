/**
 * 
 */
package com.juststocks.tradebot.util;

/**
 * @author bharath_kandasamy
 *
 */
public interface HttpUtil<T> {

	T executeGetRequest(String uri);
	
	T executePostRequest();
	
}
