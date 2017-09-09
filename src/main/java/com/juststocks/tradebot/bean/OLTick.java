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
}