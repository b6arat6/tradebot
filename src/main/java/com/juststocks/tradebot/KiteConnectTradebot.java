/**
 * 
 */
package com.juststocks.tradebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.facade.KiteConnectClientFacade;

/**
 * @author bharath_kandasamy
 *
 */
@Service
public class KiteConnectTradebot implements Tradebot {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KiteConnectTradebot.class);
	
	@Autowired
	public KiteConnectProperties properties;
	
	@Autowired
	private KiteConnectClientFacade clientFacade;
	
	@Override
	public boolean run(String[] args) {
		LOGGER.info(LOG_METHOD_ENTRY);
		if (init(args)) {
			execute();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return false;
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
		if (clientFacade.login()) {
			LOGGER.info(LOG_LOGIN_SUCCESS);
			if (clientFacade.authenticate()) {
				LOGGER.info(LOG_AUTHENTICATION_SUCCESS);
				if (clientFacade.loadParameters()) {
					LOGGER.info(LOG_PARAMETER_LOAD_SUCCESS);
					if (clientFacade.getInstruments(
							properties.getParameterData().getExchange().get(properties.getStrategyOHLExchangeIndex()))) {
						LOGGER.info(LOG_EXCHANGE_INSTRUMENTS_GET_SUCCESS);
						if (clientFacade.initWebSocket()) {
							LOGGER.info(LOG_WEB_SOCKECT_INIT_SUCCESS);
						}
					}
				}
			}
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}

}
