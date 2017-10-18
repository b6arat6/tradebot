/**
 * 
 */
package com.juststocks.tradebot.constants;

/**
 * @author bharath_kandasamy
 *
 */
public enum TradeTriggerEnum {
//  1-OHL
//  1-OL, 2-OH, 3-HL
	
	TRADE_STRATEGY_1("1"),
	TRADE_STRATEGY_TYPE_1("1"), TRADE_STRATEGY_TYPE_2("2"), TRADE_STRATEGY_TYPE_3("3"),
	
	INSTRUMENT_TYPE_EQ("EQ"), INSTRUMENT_TYPE_FUT("FUT"),
	TRADE_TYPE_QUANTITY("1"), TRADE_TYPE_VALUE("2"), TRADE_TYPE_TOTAL_VALUE("3");
	
	String value;
	
	private TradeTriggerEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
