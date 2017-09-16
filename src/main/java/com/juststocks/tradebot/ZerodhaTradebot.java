/**
 * 
 */
package com.juststocks.tradebot;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juststocks.tradebot.bean.KiteProperties;
import com.juststocks.tradebot.exception.AuthException;
import com.juststocks.tradebot.facade.KiteTradeSystemFacade;

/**
 * @author bharath_kandasamy
 *
 */
@Service
public class ZerodhaTradebot implements Tradebot {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_MAIN);
	
	@Autowired
	public KiteProperties properties;
	
	@Autowired
	private KiteTradeSystemFacade tradeSystemFacade;
	
	@Override
	public boolean run(String[] args) {
		LOGGER.info(METHOD_ENTRY);
		if (init(args)) {
			if (!execute()) {
				LOGGER.info(METHOD_EXIT);
				return false;
			}
		}
		LOGGER.info(METHOD_EXIT);
		return true;
	}

	@Override
	public boolean init(String[] args) {
		LOGGER.info(METHOD_ENTRY);
		properties.setUserId(args[0]);
		properties.setApikey(args[1]);
		properties.setApiSecret(args[2]);
		properties.setAuthUriMap();
		LOGGER.info(METHOD_EXIT);
		return true;
	}

	@Override
	public boolean execute() {
		LOGGER.info(METHOD_ENTRY);
		if (tradeSystemFacade.login()) {
			LOGGER.info(LOGIN_SUCCESS);
			try {
				if (tradeSystemFacade.authenticate()) {
					LOGGER.info(AUTHENTICATION_SUCCESS);
					if (tradeSystemFacade.loadParameters()) {
						LOGGER.info(PARAMETER_LOAD_SUCCESS);
						if (tradeSystemFacade.getInstruments(
								properties.getParameterData().getExchange().get(properties.getOhlStrategyExchangeValueIndex()))) {
							LOGGER.info(EXCHANGE_INSTRUMENTS_GET_SUCCESS);
							if (tradeSystemFacade.initWebSocket()) {
								LOGGER.info(WEB_SOCKECT_INIT_SUCCESS);
								if (tradeSystemFacade.subscribeInstruments((ArrayList<Long>) properties.getTradingTokens())) {
									LOGGER.info(INSTRUMENTS_SUBSCRIPTION_SUCCESS);
								}
							}
						}
					}
				} else {
					LOGGER.info(METHOD_EXIT);
					return false;
				}
			} catch (AuthException e) {
				LOGGER.error(e.getMessage());
				LOGGER.info(METHOD_EXIT);
				return false;
			}
		}
		LOGGER.info(METHOD_EXIT);
		return true;
	}

	@Override
	public boolean shutdown() {
		return true;
	}

}
