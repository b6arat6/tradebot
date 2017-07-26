/**
 * 
 */
package com.juststocks.tradebot.bot;

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
	public KiteConnectProperties kiteConnectProperties;
	
	@Autowired
	private KiteConnectClientFacade kiteConnectClientFacade;

	@Override
	public boolean init(String[] args) {
		LOGGER.info(LOG_METHOD_ENTRY);
		kiteConnectProperties.setUserId(args[0]);
		kiteConnectProperties.setApikey(args[1]);
		kiteConnectProperties.setApiSecret(args[2]);
		kiteConnectProperties.setAuthUriMap();
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}

	@Override
	public boolean process() {
		LOGGER.info(LOG_METHOD_ENTRY);
		if (kiteConnectClientFacade.login()) {
			if (kiteConnectClientFacade.authenticate()) {
				kiteConnectClientFacade.loadParameters();
			}
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return true;
	}

	@Override
	public boolean execute(String[] args) {
		LOGGER.info(LOG_METHOD_ENTRY);
		if (init(args)) {
			process();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return false;
	}

}
