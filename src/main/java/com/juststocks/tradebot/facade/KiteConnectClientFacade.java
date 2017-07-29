/**
 * 
 */
package com.juststocks.tradebot.facade;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

/**
 * @author bharath_kandasamy
 *
 */
@Service
public class KiteConnectClientFacade implements GenericClientFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(KiteConnectClientFacade.class);
	
	@Autowired
	public KiteConnectProperties properties;
	
	@Autowired
	public ApacheHttpUtil apacheHttpUtil;
	
	@Autowired
	private SpringRestTemplateUtil springRestTemplateUtil;
	
	private KiteConnect kiteConnect;
	
	private KiteTicker kiteTicker;
	
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
			LOGGER.info(LOG_RESPONSE_EXCHANGE_INSTRUMENTS, exchange, instruments);
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
		this.kiteTicker = new KiteTicker(kiteConnect);
		ArrayList<Long> tokens = (ArrayList<Long>) properties.getTokens(
				properties.getParameterData().getExchange().get(ParameterData.IndexEnum.EXCHANGE_NFO.getIndex())
				, KITE_CONNECT_PARAMETER_DATA_INSTRUMENT_TYPE_FUT
				, Month.AUGUST.toString().substring(0, 3));
		kiteTicker.setOnConnectedListener(new OnConnect() {
			@Override
			public void onConnected() {
				LOGGER.info(LOG_WEB_SOCKECT_CONNECTION_SUCCESS);
			}
		});
		kiteTicker.setOnDisconnectedListener(new OnDisconnect() {
			@Override
			public void onDisconnected() {
				LOGGER.error(LOG_WEB_SOCKECT_DISCONNECTION);
			}
		});
		kiteTicker.setOnTickerArrivalListener(new OnTick() {
			@Override
			public void onTick(ArrayList<Tick> ticks) {
				for (Tick tick : ticks) {
					if (tick.getOpenPrice() == tick.getLowPrice()) {
						LOGGER.info("Buy(+)={}, {}", properties.getTokenMap().get(tick.getToken()), tick.getHighPrice() - tick.getLowPrice());
					}
					if (tick.getOpenPrice() == tick.getHighPrice()) {
						LOGGER.info("Sell(-)={}, {}", properties.getTokenMap().get(tick.getToken()), tick.getLowPrice() - tick.getHighPrice());
					}
				}
			}
		});
		kiteTicker.setMode(tokens, KiteTicker.modeFull);
		try {
			kiteTicker.connect();
			kiteTicker.subscribe(tokens);
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
		LOGGER.info(LOG_METHOD_ENTRY);
		return true;
	}

}
