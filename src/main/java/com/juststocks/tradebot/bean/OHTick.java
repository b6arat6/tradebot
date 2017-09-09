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
	
	public boolean isOH() {
		return tick.getOpenPrice() == tick.getHighPrice();
	}
}