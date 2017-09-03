package com.juststocks.tradebot.bean;

import com.rainmatter.models.Tick;

/**
 * @author bharath_kandasamy
 *
 */
public class OHTick extends OHLTick {
	public OHTick(Tick tick) {
		super(tick);
	}
	
	@Override
	public int compareTo(OHLTick ohlTick) {
		if (super.compareTo(ohlTick) == 0) {
			return 0;
		} else {
			if (getNetLowChange() < ohlTick.getNetLowChange()
//					&& !isTbGreaterThanTs()
					&& getNetHighChange() < ohlTick.getNetHighChange()) {
				return -1;
			} else if (getNetLowChange() > ohlTick.getNetLowChange()
//					&& !isTbGreaterThanTs()
					&& getNetHighChange() > ohlTick.getNetHighChange()) {
				return 1;
			} else if (getNetLowChange() > ohlTick.getNetLowChange()
//					|| !isTbGreaterThanTs()
					|| getNetHighChange() > ohlTick.getNetHighChange()) {
				return 1;
			} else if (getNetLowChange() > ohlTick.getNetLowChange()
//					|| !isTbGreaterThanTs()
					|| getNetHighChange() > ohlTick.getNetHighChange()) {
				return 1;
			}
			return 0;
		}
	}
}