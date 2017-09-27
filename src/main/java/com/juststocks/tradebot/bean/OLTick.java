package com.juststocks.tradebot.bean;

import com.rainmatter.models.Tick;

/**
 * @author bharath_kandasamy
 *
 */
public class OLTick extends OHLTick {
	public OLTick(Tick tick) {
		super(tick);
	}
	
	public boolean isOL() {
		return tick.getOpenPrice() == tick.getLowPrice();
	}
	
	public int compareTo(OHLTick otherTick) {
		if (tick.getToken() == otherTick.tick.getToken()) {
			return 0;
		} else if (getTotalNetLowHighChange() < otherTick.getTotalNetLowHighChange()
				&& (!totalBuySellConstraintEnabled || isTbGreaterThanTs())) {
			return -1;
		} else {
			return 1;
		}
	}
	
}