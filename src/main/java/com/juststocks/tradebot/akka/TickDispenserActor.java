/**
 * 
 */
package com.juststocks.tradebot.akka;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.rainmatter.models.Tick;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * @author bharath_kandasamy
 *
 */
public class TickDispenserActor extends AbstractActor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TickDispenserActor.class);
	
	public static Props props(KiteConnectProperties kiteConnectProperties, ActorRef ohlTradeStrategyActorRef) {
		return Props.create(TickDispenserActor.class, () -> new TickDispenserActor(kiteConnectProperties, ohlTradeStrategyActorRef));
	}

	private KiteConnectProperties kiteConnectProperties;
	
	private ActorRef ohlTradeStrategyActorRef;
	
	public TickDispenserActor(KiteConnectProperties kiteConnectProperties, ActorRef ohlTradeStrategyActorRef) {
		this.kiteConnectProperties = kiteConnectProperties;
		this.ohlTradeStrategyActorRef = ohlTradeStrategyActorRef;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MyArrayList.class, myArrayList -> {
			for (Tick tick : myArrayList.ticks) {
				ohlTradeStrategyActorRef.tell(tick, ActorRef.noSender());
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
