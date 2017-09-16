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
	
	@Override
	public int compareTo(OHLTick o) {
		OLTick otherTick = (OLTick) o;
		if (tick.getToken() == otherTick.getToken()) {
			return 0;
		} else if (!lenientNLHC
				&& getNetLowChange() < o.getNetLowChange()
				&& getNetHighChange() < o.getNetHighChange()
				&& (!totalBuySellConstraintEnabled || isTbGreaterThanTs())) {
			return -1;
		} else if (lenientNLHC
				&& (getNetLowChange() < o.getNetLowChange() || getNetHighChange() < o.getNetHighChange())
				&& (!totalBuySellConstraintEnabled || isTbGreaterThanTs())) {
			return -1;
		} else {
			return 1;
		}
	}
}