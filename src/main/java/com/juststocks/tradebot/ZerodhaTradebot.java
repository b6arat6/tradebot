/**
 * 
 */
package com.juststocks.tradebot;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.facade.KiteConnectTradeSystemFacade;

/**
 * @author bharath_kandasamy
 *
 */
@Service
public class ZerodhaTradebot implements Tradebot {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZerodhaTradebot.class);
	
	@Autowired
	public KiteConnectProperties properties;
	
	@Autowired
	private KiteConnectTradeSystemFacade tradeSystemFacade;
	
	@Override
	public boolean run(String[] args) {
		LOGGER.info(LOG_METHOD_ENTRY);
		if (init(args)) {
			execute();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}

	@Override
	public boolean init(String[] args) {
		LOGGER.info(LOG_METHOD_ENTRY);
		properties.setUserId(args[0]);
		properties.setApikey(args[1]);
		properties.setApiSecret(args[2]);
		properties.setAuthUriMap();
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}

	@Override
	public boolean execute() {
		LOGGER.info(LOG_METHOD_ENTRY);
		if (tradeSystemFacade.login()) {
			LOGGER.info(LOG_LOGIN_SUCCESS);
			if (tradeSystemFacade.authenticate()) {
				LOGGER.info(LOG_AUTHENTICATION_SUCCESS);
				if (tradeSystemFacade.loadParameters()) {
					LOGGER.info(LOG_PARAMETER_LOAD_SUCCESS);
					if (tradeSystemFacade.getInstruments(
							properties.getParameterData().getExchange().get(properties.getStrategyOHLExchangeIndex()))) {
						LOGGER.info(LOG_EXCHANGE_INSTRUMENTS_GET_SUCCESS);
						if (tradeSystemFacade.initWebSocket()) {
							LOGGER.info(LOG_WEB_SOCKECT_INIT_SUCCESS);
							if (tradeSystemFacade.subscribeInstruments((ArrayList<Long>) properties.getTokens())) {
								LOGGER.info(LOG_INSTRUMENTS_SUBSCRIPTION_SUCCESS);
							}
						}
					}
				}
			}
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}

}
