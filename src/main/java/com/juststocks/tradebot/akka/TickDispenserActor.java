/**
 * 
 */
package com.juststocks.tradebot.akka;

import java.util.ArrayList;
import java.util.List;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_MAIN);
	
	public static Props props(KiteProperties kiteProperties, ActorRef ohlTradeStrategyActorRef) {
		return Props.create(TickDispenserActor.class, () -> new TickDispenserActor(kiteProperties, ohlTradeStrategyActorRef));
	}

	private KiteProperties kiteProperties;
	
	private ActorRef ohlTradeStrategyActorRef;
	
	public TickDispenserActor(KiteProperties kiteProperties, ActorRef ohlTradeStrategyActorRef) {
		this.kiteProperties = kiteProperties;
		this.ohlTradeStrategyActorRef = ohlTradeStrategyActorRef;
	}

	@Override
	public Receive createReceive() {
//		List<Tick> ticks = new ArrayList<>();
		return receiveBuilder().match(MyArrayList.class, myArrayList -> {
			for (Tick tick : myArrayList.ticks) {
				if (tick.getToken() == 12951554 || tick.getToken() == 12903170 || tick.getToken() == 12948994) {
//					ticks.add(tick);
					ohlTradeStrategyActorRef.tell(tick, ActorRef.noSender());
				}
			}
//			while (true) {
//				for (Tick tick : ticks) {
//					tick.setLastTradedPrice(tick.getLastTradedPrice() + 10);
//					ohlTradeStrategyActorRef.tell(tick, ActorRef.noSender());
//				}
//			}
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
