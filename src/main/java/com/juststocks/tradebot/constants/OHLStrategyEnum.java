/**
 * 
 */
package com.juststocks.tradebot.constants;

/**
 * @author bharath_kandasamy
 *
 */
public enum OHLStrategyEnum {
	
	MODE_OL("1"), MODE_OH("2"), MODE_OHL("3"),
	INSTRUMENT_TYPE_EQ("EQ"), INSTRUMENT_TYPE_FUT("FUT"),
	TRADE_TYPE_QUANTITY("1"), TRADE_TYPE_VALUE("2"), TRADE_TYPE_TOTAL_VALUE("3");
	
	String value;
	
	private OHLStrategyEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
