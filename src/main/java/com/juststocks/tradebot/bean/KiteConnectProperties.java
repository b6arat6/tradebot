/**
 * 
 */
package com.juststocks.tradebot.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.rainmatter.models.Instrument;

/**
 * @author bharath_kandasamy
 *
 */
@ConfigurationProperties("com.juststocks.tradebot.kiteconnect")
public class KiteConnectProperties implements TradebotConstants {
	
	private String userId;
	
	private String apikey;
	
	private String rootApiEndpoint;
	
	private String loginEndpoint;
	
	private String apiSecret;
	
	private String requestToken;
	
	private String accessToken;
	
	private String publicToken;
	
	private String parameterApiPath;
	
	private Map<String, String> authUriMap;
	
	private ParameterData parameterData;
	
	private Map<String, List<Instrument>> instrumentMap = new HashMap<>();
	
	private List<Long> tokens = new ArrayList<>();
	
	private Map<Long, String> tokenMap = new HashMap<>();
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApiEndpoint(String path) {
		return rootApiEndpoint.concat(path)
				.concat(SYMBOL_QUESTION)
				.concat(KITE_CONNECT_QUERY_PARAM_API_KEY).concat(apikey)
				.concat(SYMBOL_AMPERSAND)
				.concat(KITE_CONNECT_QUERY_PARAM_ACCESS_TOKEN).concat(accessToken);
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String kiteConnectApikey) {
		this.apikey = kiteConnectApikey;
	}

	public String getRootApiEndpoint() {
		return rootApiEndpoint;
	}

	public void setRootApiEndpoint(String rootApiEndpoint) {
		this.rootApiEndpoint = rootApiEndpoint;
	}

	public String getLoginEndpoint() {
		return loginEndpoint;
	}

	public void setLoginEndpoint(String loginEndpoint) {
		this.loginEndpoint = loginEndpoint;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getPublicToken() {
		return publicToken;
	}

	public void setPublicToken(String publicToken) {
		this.publicToken = publicToken;
	}

	public String getParameterApiPath() {
		return parameterApiPath;
	}

	public void setParameterApiPath(String parameterApiPath) {
		this.parameterApiPath = parameterApiPath;
	}

	public Map<String, String> getAuthUriMap() {
		return authUriMap;
	}

	public void setAuthUriMap() {
		this.authUriMap = new HashMap<>();
		authUriMap.put("api_key", apikey);
		authUriMap.put("access_token", accessToken);
	}

	public ParameterData getParameterData() {
		return parameterData;
	}

	public void setParameterData(ParameterData parameterData) {
		this.parameterData = parameterData;
	}

	public Map<String, List<Instrument>> getInstrumentMap() {
		return instrumentMap;
	}

	public void setInstrumentMap(String exchange, List<Instrument> instruments) {
		instrumentMap.put(exchange, instruments);
		setTokenMap(instruments);
	}

	public List<Long> getTokens(String exchange, String instrumentType, String expiry) {
		for (Instrument instrument : getInstrumentMap().get(exchange)) {
			if (instrument.getInstrument_type().equals(instrumentType) && instrument.getTradingsymbol().contains(expiry.toUpperCase())) {
				tokens.add(instrument.getInstrument_token());
			}
		}
		return tokens;
	}

	public void setTokens(List<Long> tokens) {
		this.tokens = tokens;
	}

	public Map<Long, String> getTokenMap() {
		return tokenMap;
	}

	public void setTokenMap(List<Instrument> instruments) {
		for (Instrument instrument : instruments) {
			tokenMap.put(instrument.getInstrument_token(), instrument.getTradingsymbol());
		}
	}

}