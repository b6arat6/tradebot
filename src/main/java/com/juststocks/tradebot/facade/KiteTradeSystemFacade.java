/**
 * 
 */
package com.juststocks.tradebot.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.juststocks.tradebot.akka.TickDispenserActor;
import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.bean.response.kiteconnect.KiteResponse;
import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.KiteOrderParamNameEnum;
import com.juststocks.tradebot.constants.KiteOrderParamValuesEnum;
import com.juststocks.tradebot.exception.AuthException;
import com.juststocks.tradebot.util.ApacheHttpUtil;
import com.juststocks.tradebot.util.SpringRestTemplateUtil;
import com.neovisionaries.ws.client.WebSocketException;
import com.rainmatter.kiteconnect.KiteConnect;
import com.rainmatter.kitehttp.SessionExpiryHook;
import com.rainmatter.kitehttp.exceptions.KiteException;
import com.rainmatter.models.IndicesQuote;
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
public class KiteTradeSystemFacade implements TradeSystemFacade, SessionExpiryHook, OnConnect, OnDisconnect, OnTick {
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_MAIN);

	@Autowired
	public KiteProperties properties;

	@Autowired
	public ApacheHttpUtil apacheHttpUtil;

	@Autowired
	private SpringRestTemplateUtil springRestTemplateUtil;

	private KiteConnect kiteConnect;

	private KiteTicker kiteTicker;

	public boolean webSocketConnected;

	@Autowired
	@Qualifier(AKKA_TICK_DISPENSER_ACTOR_REF)
	private ActorRef tickDispenserActorRef;

	@Autowired
	@Qualifier(AKKA_ORDER_ACTOR_REF)
	private ActorRef orderActorRef;

	public KiteConnect getKiteConnect() {
		return kiteConnect;
	}

	public void setKiteConnect(KiteConnect kiteConnect) {
		this.kiteConnect = kiteConnect;
	}

	@Override
	public boolean login() {
		return (properties.getRequestToken() != null);
	}

	@Override
	public boolean authenticate() throws AuthException {
		LOGGER.info(METHOD_ENTRY);
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
				throw new AuthException(EXCEPTION_AUTH.replace(REPLACE_HOLDER_CODE, String.valueOf(e.code))
						.replace(REPLACE_HOLDER_ERROR, e.message));
			}
			LOGGER.info(KITE_CONNECT_AT_PT, userModel.accessToken, userModel.publicToken);
			LOGGER.info(METHOD_EXIT);
			return false;

			// kiteConnect.setAccessToken(userModel.accessToken);
			// kiteConnect.setPublicToken(userModel.publicToken);
			//
			// properties.setAccessToken(userModel.accessToken);
			// properties.setPublicToken(userModel.publicToken);
		} else {
			kiteConnect.setAccessToken(properties.getAccessToken());
			kiteConnect.setPublicToken(properties.getPublicToken());
		}
		kiteConnect.registerHook(this);
		LOGGER.info(METHOD_EXIT);
		return (kiteConnect.getAccessToken() != null);
	}

	@Override
	public void sessionExpired() {
		LOGGER.info(METHOD_ENTRY);
		LOGGER.error(SESSION_EXPIRED);
		LOGGER.info(METHOD_EXIT);
	}

	@Override
	public boolean loadParameters() {
		LOGGER.info(METHOD_ENTRY);
		KiteResponse<ParameterData> response = springRestTemplateUtil
				.exchange(properties.getApiEndpoint(properties.getParameterApiPath()), HttpMethod.GET, null,
						new ParameterizedTypeReference<KiteResponse<ParameterData>>() {
						})
				.getBody();
		properties.setParameterData(response.getData());
		LOGGER.info(RESPONSE_PARAMETERS, response.getData().toString());
		LOGGER.info(METHOD_EXIT);
		return true;
	}

	@Override
	public boolean getInstruments() {
		// TODO
		return false;
	}

	@Override
	public boolean getInstruments(String exchange) {
		LOGGER.info(METHOD_ENTRY);
		List<Instrument> instruments = null;
		try {
			instruments = kiteConnect.getInstruments(exchange);
			properties.setInstrumentMap(exchange, instruments);
			LOGGER.debug(RESPONSE_EXCHANGE_INSTRUMENTS, exchange, instruments);
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
		LOGGER.info(METHOD_EXIT);
		return instruments.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getQuoteIndices(Long token) {
		IndicesQuote indicesQuote = null;
		try {
			indicesQuote = kiteConnect.getQuoteIndices(
					properties.getParameterData().getExchange().get(properties.getOhlStrategyExchangeIndex())
					, properties.getTradingSymbolMap().get(token));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) indicesQuote;
	}

	@Override
	public boolean initWebSocket() {
		LOGGER.info(METHOD_ENTRY);
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
		LOGGER.info(METHOD_EXIT);
		return connected;
	}

	@Override
	public void onConnected() {
		LOGGER.info(METHOD_ENTRY);
		this.webSocketConnected = true;
		LOGGER.info(WEB_SOCKECT_CONNECTION_SUCCESS);
		LOGGER.info(METHOD_EXIT);
	}

	@Override
	public boolean subscribeInstruments(ArrayList<Long> tokens) {
		LOGGER.info(METHOD_ENTRY);
		boolean subscribed = false;
		try {
			kiteTicker.setMode(tokens, KiteTicker.modeFull);
			kiteTicker.subscribe(tokens);
			subscribed = true;
			LOGGER.debug(INSTRUMENTS_SUBSCRIBED, tokens.size(), tokens.toString());
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
		LOGGER.info(METHOD_EXIT);
		return subscribed;
	}

	@Override
	public boolean unsubscribeInstruments(ArrayList<Long> tokens) {
		LOGGER.debug(METHOD_ENTRY);
		boolean unsubscribed = false;
		kiteTicker.unsubscribe(tokens);
		unsubscribed = true;
		LOGGER.debug(METHOD_EXIT);
		return unsubscribed;

	}

	@Override
	public void onTick(ArrayList<Tick> ticks) {
		LOGGER.debug(METHOD_ENTRY);
		if (ticks.size() > 0) {
			LOGGER.info(KITE_ON_TICK_SIZE, ticks.size());
			tickDispenserActorRef.tell(new TickDispenserActor.MyArrayList(ticks), ActorRef.noSender());
		}
		LOGGER.debug(METHOD_EXIT);
	}

	@Override
	@Scheduled(cron = CRON_ENTRY_OHL_STRATEGY_ORDERS)
	public void triggerOHLStrategyOrders() {
		orderActorRef.tell(ACTOR_ORDER_MSG_TYPE_OHL_STRATEGY, ActorRef.noSender());
	}

	@Override
	public void onDisconnected() {
		LOGGER.info(METHOD_ENTRY);
		this.webSocketConnected = false;
		LOGGER.error(WEB_SOCKECT_DISCONNECTION);
		LOGGER.info(METHOD_EXIT);
	}

	@Override
	public Map<String, Object> buildOrderParamMap(
			KiteOrderParamValuesEnum exchange, 
			String tradingsymbol,
			KiteOrderParamValuesEnum transaction_type,
			int quantity,
			double price,
			KiteOrderParamValuesEnum product,
			KiteOrderParamValuesEnum order_type,
			KiteOrderParamValuesEnum validity,
			int disclosed_quantity,
			double trigger_price,
			double squareoff_value,
			double stoploss_value,
			double trailing_stoploss
			) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(KiteOrderParamNameEnum.EXCHANGE.name(), exchange.name());
		paramMap.put(KiteOrderParamNameEnum.TRADINGSYMBOL.name(), tradingsymbol);
		paramMap.put(KiteOrderParamNameEnum.TRANSACTION_TYPE.name(), transaction_type.name());
		paramMap.put(KiteOrderParamNameEnum.QUANTITY.name(), quantity);
		paramMap.put(KiteOrderParamNameEnum.PRICE.name(), price);
		paramMap.put(KiteOrderParamNameEnum.PRODUCT.name(), product.name());
		paramMap.put(KiteOrderParamNameEnum.ORDER_TYPE.name(), order_type.name());
		paramMap.put(KiteOrderParamNameEnum.VALIDITY.name(), validity.name());
		if (disclosed_quantity >= 0)
			paramMap.put(KiteOrderParamNameEnum.DISCLOSED_QUANTITY.name(), disclosed_quantity);
		if (trigger_price >= 0)
			paramMap.put(KiteOrderParamNameEnum.TRIGGER_PRICE.name(), trigger_price);
		if (squareoff_value >= 0)
			paramMap.put(KiteOrderParamNameEnum.SQUAREOFF_VALUE.name(), squareoff_value);
		if (stoploss_value >= 0)
			paramMap.put(KiteOrderParamNameEnum.STOPLOSS_VALUE.name(), stoploss_value);
		if (trailing_stoploss >= 0)
			paramMap.put(KiteOrderParamNameEnum.TRAILING_STOPLOSS.name(), trailing_stoploss);
		return paramMap;
	}

}
