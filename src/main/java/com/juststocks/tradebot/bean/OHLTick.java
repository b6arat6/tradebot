package com.juststocks.tradebot.bean;

import com.rainmatter.models.Tick;

/**
 * @author bharath_kandasamy
 *
 */
public class OHLTick extends Tick implements Comparable<OHLTick> {
	Tick tick;

	public OHLTick(Tick tick) {
		this.tick = tick;
	}
	
	@Override
	public int compareTo(OHLTick ohlTick) {
		Tick otherTick = ohlTick.tick;

		double thisTickNetLowChange = (tick.getLastTradedPrice() - tick.getLowPrice()) / tick.getLowPrice();
		double otherTickNetLowChange = (otherTick.getLastTradedPrice() - otherTick.getLowPrice()) / otherTick.getLowPrice();

		double thisTickNetHighChange = (tick.getHighPrice() - tick.getLastTradedPrice()) / tick.getHighPrice();
		double otherTickNetHighChange = (otherTick.getHighPrice() - otherTick.getLastTradedPrice())	/ otherTick.getHighPrice();

		// boolean thisTickTbTsState = tick.getTotalBuyQuantity() > tick.getTotalSellQuantity();
		// boolean otherTickTbTsState = otherTick.getTotalBuyQuantity() > otherTick.getTotalSellQuantity();

		if (thisTickNetLowChange < otherTickNetLowChange
//				&& thisTickTbTsState
				&& thisTickNetHighChange < otherTickNetHighChange) {
			return -1;
		} else if (thisTickNetLowChange > otherTickNetLowChange
//				&& otherTickTbTsState
				&& thisTickNetHighChange > otherTickNetHighChange) {
			return 1;
		}
		return (int) (tick.getToken() - otherTick.getToken());
	}
	
}