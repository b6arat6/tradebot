/**
 * 
 */
package com.juststocks.tradebot.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.juststocks.tradebot.akka.OHLTradeStrategyActor;
import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.bean.response.kiteconnect.KiteConnectResponse;
import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.util.ApacheHttpUtil;
import com.juststocks.tradebot.util.SpringRestTemplateUtil;
import com.neovisionaries.ws.client.WebSocketException;
import com.rainmatter.kiteconnect.KiteConnect;
import com.rainmatter.kitehttp.SessionExpiryHook;
import com.rainmatter.kitehttp.exceptions.KiteException;
import com.rainmatter.models.Instrument;
import com.rainmatter.models.Tick;
import com.rainmatter.models.UserModel;
import com.rainmatter.ticker.KiteTicker;
import com.rainmatter.ticker.OnConnect;
import com.rainmatter.ticker.OnDisconnect;
import com.rainmatter.ticker.OnTick;

import akka.actor.ActorRef;

/**
 * @author bharath_kandasamy
 *
 */
@Service
public class KiteConnectClientFacade implements GenericClientFacade, OnConnect, OnDisconnect, OnTick {
	private static final Logger LOGGER = LoggerFactory.getLogger(KiteConnectClientFacade.class);
	
	@Autowired
	public KiteConnectProperties properties;
	
	@Autowired
	public ApacheHttpUtil apacheHttpUtil;
	
	@Autowired
	private SpringRestTemplateUtil springRestTemplateUtil;
	
	private KiteConnect kiteConnect;
	
	private KiteTicker kiteTicker;
	
	public boolean webSocketConnected;
	
	public KiteConnect getKiteConnect() {
		return kiteConnect;
	}

	public void setKiteConnect(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
	}

	@Autowired
	@Qualifier(AKKA_OHL_TRADE_STRATEGY_ACTOR)
	private ActorRef ohlTradeStrategyActor;
	
	@Override
	public boolean login() {
		return (properties.getRequestToken() != null);
	}

	@Override
	public boolean authenticate() {
		LOGGER.info(LOG_METHOD_ENTRY);
		this.kiteConnect = new KiteConnect(properties.getApikey());
		kiteConnect.setUserId(properties.getUserId());
		UserModel userModel = null;
		if (StringUtils.isEmpty(properties.getAccessToken()) && StringUtils.isEmpty(properties.getPublicToken())) {
			try {
				userModel = kiteConnect.requestAccessToken(properties.getRequestToken(), properties.getApiSecret());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KiteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kiteConnect.setAccessToken(userModel.accessToken);
			kiteConnect.setPublicToken(userModel.publicToken);

			properties.setAccessToken(userModel.accessToken);
			properties.setPublicToken(userModel.publicToken);
			
			LOGGER.info("accessToken={}", kiteConnect.getAccessToken());
			LOGGER.info("publicToken={}", kiteConnect.getPublicToken());
		} else {
			kiteConnect.setAccessToken(properties.getAccessToken());
			kiteConnect.setPublicToken(properties.getPublicToken());
		}
		kiteConnect.registerHook(new SessionExpiryHook() {
			@Override
			public void sessionExpired() {
				LOGGER.error(LOG_SESSION_EXPIRED);				
			}
		});
		LOGGER.info(LOG_METHOD_EXIT);
		return (kiteConnect.getAccessToken() != null);
	}

	@Override
	public boolean loadParameters() {
		LOGGER.info(LOG_METHOD_ENTRY);
		KiteConnectResponse<ParameterData> response	= springRestTemplateUtil.exchange(
				properties.getApiEndpoint(properties.getParameterApiPath())
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<KiteConnectResponse<ParameterData>>() {
		}).getBody();
		properties.setParameterData(response.getData());
		LOGGER.info(LOG_RESPONSE_PARAMETERS, response.getData().toString());
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}
	
	@Override
	public boolean getInstruments() {
		return false;
	}

	@Override
	public boolean getInstruments(String exchange) {
		LOGGER.info(LOG_METHOD_ENTRY);
		List<Instrument> instruments = null;
		try {
			instruments = kiteConnect.getInstruments(exchange);
			properties.setInstrumentMap(exchange, instruments);
			LOGGER.debug(LOG_RESPONSE_EXCHANGE_INSTRUMENTS, exchange, instruments);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return instruments.size() > 0;
	}

	@Override
	public boolean initWebSocket() {
		LOGGER.info(LOG_METHOD_ENTRY);
		boolean connected = false;
		this.kiteTicker = new KiteTicker(kiteConnect);
		kiteTicker.setOnConnectedListener(this);
		kiteTicker.setOnDisconnectedListener(this);
		kiteTicker.setOnTickerArrivalListener(this);
		try {
			kiteTicker.connect();
			connected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return connected;
	}
	
	@Override
	public void onConnected() {
		LOGGER.info(LOG_METHOD_ENTRY);
		this.webSocketConnected = true;
		LOGGER.info(LOG_WEB_SOCKECT_CONNECTION_SUCCESS);
		LOGGER.info(LOG_METHOD_EXIT);
	}
	
	@Override
	public boolean subscribeInstruments(ArrayList<Long> tokens) {
		LOGGER.info(LOG_METHOD_ENTRY);
		boolean subscribed = false;
		try {
			kiteTicker.setMode(tokens, KiteTicker.modeFull);
			kiteTicker.subscribe(tokens);
			subscribed = true;
			LOGGER.debug(LOG_INSTRUMENTS_SUBSCRIBED, tokens.size(), tokens.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return subscribed;
	}
	
	@Override
	public boolean unsubscribeInstruments(ArrayList<Long> tokens) {
		LOGGER.info(LOG_METHOD_ENTRY);
		boolean unsubscribed = false;
		kiteTicker.unsubscribe(tokens);
		unsubscribed = true;
		LOGGER.debug(LOG_INSTRUMENTS_UNSUBSCRIBED, tokens.size(), tokens.toString());
		LOGGER.info(LOG_METHOD_EXIT);
		return unsubscribed;
	}
	
	@Override
	public void onTick(ArrayList<Tick> ticks) {
		LOGGER.info(LOG_METHOD_ENTRY);
		if (ticks.size() > 0) {
			ohlTradeStrategyActor.tell(new OHLTradeStrategyActor.MyArrayList(ticks), ActorRef.noSender());
		}
		LOGGER.info(OL_TICK_MAP, KiteConnectProperties.olTickMap.toString());
		LOGGER.info(OH_TICK_MAP, KiteConnectProperties.ohTickMap.toString());
		LOGGER.info(LOG_METHOD_EXIT);
	}

	@Override
	public void onDisconnected() {
		LOGGER.info(LOG_METHOD_ENTRY);
		this.webSocketConnected = false;
		LOGGER.error(LOG_WEB_SOCKECT_DISCONNECTION);
		LOGGER.info(LOG_METHOD_EXIT);
	}

}
