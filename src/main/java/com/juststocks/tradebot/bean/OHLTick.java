package com.juststocks.tradebot.bean;

import com.rainmatter.models.Tick;

/**
 * @author bharath_kandasamy
 *
 */
public class OHLTick extends Tick implements Comparable<OHLTick> {
	final Tick tick;

	public OHLTick(Tick tick) {
		this.tick = tick;
	}
	
	public double getNetLowChange() {
		return ((tick.getLastTradedPrice() - tick.getLowPrice()) / tick.getLowPrice());
	}
	
	public double getNetHighChange() {
		return ((tick.getHighPrice() - tick.getLastTradedPrice()) / tick.getHighPrice());
	}
	
	public boolean isTbGreaterThanTs() {
		return tick.getTotalBuyQuantity() > tick.getTotalSellQuantity();
	}
	
	@Override
	public int compareTo(OHLTick ohlTick) {
		return (int) (tick.getToken() - ohlTick.tick.getToken());
	}
	
}