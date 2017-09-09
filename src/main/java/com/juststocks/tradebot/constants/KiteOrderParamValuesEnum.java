/**
 * 
 */
package com.juststocks.tradebot.constants;

/**
 * @author bharath_kandasamy
 *
 */
public enum KiteOrderParamValuesEnum {
	EXCHANGE_NSE("NSE"), EXCHANGE_BSE("BSE"), EXCHANGE_NFO("NFO"), EXCHANGE_BFO("BFO"), EXCHANGE_CDS("CDS"), EXCHANGE_MCX("MCX"),
	TRANSACTION_TYPE_BUY("BUY"), TRANSACTION_TYPE_SELL("SELL"),
	PRODUCT_NRML("NRML"), PRODUCT_MIS("MIS"), PRODUCT_CNC("CNC"),
	ORDER_TYPE_NRML("NRML"), ORDER_TYPE_SL("SL"), ORDER_TYPE_SL_M("SL-M"), ORDER_TYPE_MARKET("MARKET"), 
	VALIDITY_DAY("DAY"), VALIDITY_IOC("IOC"),
	VARIETY_BO("bo"), VARIETY_CO("co"), VARIETY_AMO("amo"), VARIETY_REGULAR("regular");

	String value;
	
	KiteOrderParamValuesEnum(String value) {
		this.value = value;
	}
}
