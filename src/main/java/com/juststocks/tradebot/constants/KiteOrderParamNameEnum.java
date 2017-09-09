/**
 * 
 */
package com.juststocks.tradebot.constants;

/**
 * @author bharath_kandasamy
 *
 */
public enum KiteOrderParamNameEnum {
	EXCHANGE("exchange"),
	TRADINGSYMBOL("tradingsymbol"),
	TRANSACTION_TYPE("transaction_type"),
	QUANTITY("quantity"),
	PRICE("price"),
	PRODUCT("product"),
	ORDER_TYPE("order_type"),
	VALIDITY("validity"),
	DISCLOSED_QUANTITY("disclosed_quantity"),
	TRIGGER_PRICE("trigger_price"),
	SQUAREOFF_VALUE("squareoff_value"),
	STOPLOSS_VALUE("stoploss_value"),
	TRAILING_STOPLOSS("trailing_stoploss"),
	VARIETY("variety");
	
	String name;
	
	KiteOrderParamNameEnum(String paramName) {
		this.name = paramName;
	}
}
