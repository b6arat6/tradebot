/**
 * 
 */
package com.juststocks.tradebot.facade;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.bean.response.kiteconnect.KiteConnectResponse;
import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.util.ApacheHttpUtil;
import com.juststocks.tradebot.util.SpringRestTemplateUtil;
import com.rainmatter.kiteconnect.KiteConnect;
import com.rainmatter.kitehttp.exceptions.KiteException;
import com.rainmatter.models.UserModel;

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
	
	@Override
	public boolean login() {
		return true;
	}

	@Override
	public boolean authenticate() {
		LOGGER.info(LOG_METHOD_ENTRY);
		try {
			this.kiteConnect = new KiteConnect(properties.getApikey());
			kiteConnect.setUserId(properties.getUserId());
			UserModel userModel;
			if (StringUtils.isEmpty(properties.getAccessToken()) && StringUtils.isEmpty(properties.getPublicToken())) {
				userModel = kiteConnect.requestAccessToken(properties.getRequestToken(), properties.getApiSecret());
				kiteConnect.setAccessToken(userModel.accessToken);
				kiteConnect.setPublicToken(userModel.publicToken);
				LOGGER.info("accessToken={}", kiteConnect.getAccessToken());
				LOGGER.info("publicToken={}", kiteConnect.getPublicToken());
			} else {
				kiteConnect.setAccessToken(properties.getAccessToken());
				kiteConnect.setPublicToken(properties.getPublicToken());
			}
		} catch (JSONException | KiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(LOG_METHOD_EXIT);
		return (kiteConnect.getAccessToken() != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean loadParameters() {
		LOGGER.info(LOG_METHOD_ENTRY);
		KiteConnectResponse<ParameterData> response
		= springRestTemplateUtil.getForObject(properties.getApiEndpoint(properties.getParameterApiPath()), KiteConnectResponse.class);
		LOGGER.info(response.getData().getExchange().toString());
		LOGGER.info(LOG_METHOD_EXIT);
		return false;
	}

}
