package com.juststocks.tradebot.bean;

import org.apache.commons.math3.util.Precision;

import com.rainmatter.models.Tick;

/**
 * @author bharath_kandasamy
 *
 */
public abstract class OHLTick extends Tick implements Comparable<OHLTick> {
	public final Tick tick;
	
	public static boolean totalBuySellConstraintEnabled;
	
	public OHLTick(Tick tick) {
		this.tick = tick;
	}
	
	public boolean isOLOrOH() {
		return tick.getOpenPrice() == tick.getLowPrice() || tick.getOpenPrice() == tick.getHighPrice();
	}
	
	public boolean isOLAndOH() {
		return tick.getOpenPrice() == tick.getLowPrice() && tick.getOpenPrice() == tick.getHighPrice();
	}
	
	public double getNetLowChange() {
		return Precision.round((tick.getLastTradedPrice() - tick.getLowPrice()) / tick.getLowPrice(), 6) * 100;
	}
	
	public double getNetHighChange() {
		return Precision.round((tick.getHighPrice() - tick.getLastTradedPrice()) / tick.getHighPrice(), 6) * 100;
	}
	
	public boolean isTbGreaterThanTs() {
		return tick.getTotalBuyQuantity() > tick.getTotalSellQuantity();
	}
	
	public double getTotalNetLowHighChange() {
		return Precision.round(getNetLowChange() + getNetHighChange(), 6);
	}
	
}