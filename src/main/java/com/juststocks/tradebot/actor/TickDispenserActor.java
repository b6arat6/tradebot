/**
 * 
 */
package com.juststocks.tradebot.actor;

import static com.juststocks.tradebot.bean.ZerodhaProperties.nonOHLTickSet;
import static com.juststocks.tradebot.bean.ZerodhaProperties.orderedTickMap;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.ZerodhaProperties;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.rainmatter.models.Tick;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class TickDispenserActor extends AbstractActor implements TradebotConstants {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_MAIN);
	
	public static Props props(ZerodhaProperties zerodhaProperties, ActorRef ohlTradeStrategyActorRef) {
		return Props.create(TickDispenserActor.class, () -> new TickDispenserActor(zerodhaProperties, ohlTradeStrategyActorRef));
	}

	@SuppressWarnings("unused")
	private ZerodhaProperties zerodhaProperties;
	
	private ActorRef ohlTradeStrategyActorRef;
	
	public TickDispenserActor(ZerodhaProperties zerodhaProperties, ActorRef ohlTradeStrategyActorRef) {
		this.zerodhaProperties = zerodhaProperties;
		this.ohlTradeStrategyActorRef = ohlTradeStrategyActorRef;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MyArrayList.class, myArrayList -> {
			for (Tick tick : myArrayList.ticks) {
				if (!nonOHLTickSet.contains(tick.getToken())
						&& !orderedTickMap.containsKey(tick.getToken())) {
					ohlTradeStrategyActorRef.tell(tick, ActorRef.noSender());
				}
			}
		}).build();
	}
	
	public static class MyArrayList extends ArrayList<Tick> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6709048444526628085L;
		
		private ArrayList<Tick> ticks;
		
		public MyArrayList(ArrayList<Tick> ticks) {
			this.ticks = ticks;
		}
	}

}
