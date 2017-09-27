/**
 * 
 */
package com.juststocks.tradebot.akka;

import static com.juststocks.tradebot.bean.KiteProperties.nonOHLTickSet;
import static com.juststocks.tradebot.bean.KiteProperties.orderedTickMap;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteProperties;
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
	
	public static Props props(KiteProperties kiteProperties, ActorRef ohlTradeStrategyActorRef) {
		return Props.create(TickDispenserActor.class, () -> new TickDispenserActor(kiteProperties, ohlTradeStrategyActorRef));
	}

	@SuppressWarnings("unused")
	private KiteProperties kiteProperties;
	
	private ActorRef ohlTradeStrategyActorRef;
	
	public TickDispenserActor(KiteProperties kiteProperties, ActorRef ohlTradeStrategyActorRef) {
		this.kiteProperties = kiteProperties;
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
