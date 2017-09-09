/**
 * 
 */
package com.juststocks.tradebot.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.rainmatter.models.Instrument;

/**
 * @author bharath_kandasamy
 *
 */
@ConfigurationProperties("com.juststocks.tradebot.kite")
public class KiteProperties implements TradebotConstants {
	
	public static Map<Long, OLTick> olTickMap = new ConcurrentHashMap<>();
	public static Map<Long, OHTick> ohTickMap = new ConcurrentHashMap<>();
	public static Set<Long> nonOHLTickSet = new ConcurrentSkipListSet<>();
	
	private String userId;
	
	private String apikey;
	
	private String rootApiEndpoint;
	
	private String loginEndpoint;
	
	private String apiSecret;
	
	private String requestToken;
	
	private String accessToken;
	
	private String publicToken;
	
	private String parameterApiPath;
	
	private int ohlStrategyExchangeIndex;
	
	private String ohlStrategyInstrumentType;
	
	private String ohlStrategyExpiryMonth;
	
	private Map<String, String> authUriMap;
	
	private ParameterData parameterData;
	
	private Map<String, List<Instrument>> instrumentMap = new HashMap<>();
	
	private List<Long> tokens = new ArrayList<>();
	
	private Map<Long, String> tradingSymbolMap = new HashMap<>();
	
	private int tickDisperserActorRoutees;
	
	private int ohlStrategyActorRoutees;
	
	private int orderActorRoutees;
	
	private int tradeableTickDataLoggingInitDelay;
	
	private int tradeableTickDataLoggingInterval;
	
	private double ohlTradeableNLC;
	
	private double ohlTradeableNHC;
	
	private boolean enableOHLTsTbConstraint;
	
	private int ohlTradeCount;
	
	private int ohlTradeTypePerInstrument;
	
	private int ohlTradeQtyPerInstrument;
	
	private int ohlTradeTxnPerInstrument;
	
	private int ohlTradeTotalTxn; 
	
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

	public int getOhlStrategyExchangeIndex() {
		return ohlStrategyExchangeIndex;
	}

	public void setOhlStrategyExchangeIndex(int ohlStrategyExchangeIndex) {
		this.ohlStrategyExchangeIndex = ohlStrategyExchangeIndex;
	}

	public String getOhlStrategyInstrumentType() {
		return ohlStrategyInstrumentType;
	}

	public void setOhlStrategyInstrumentType(String ohlStrategyInstrumentType) {
		this.ohlStrategyInstrumentType = ohlStrategyInstrumentType;
	}

	public String getOhlStrategyExpiryMonth() {
		return ohlStrategyExpiryMonth;
	}

	public void setOhlStrategyExpiryMonth(String ohlStrategyExpiryMonth) {
		this.ohlStrategyExpiryMonth = ohlStrategyExpiryMonth;
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
		setTradingSymbolMap(instruments);
	}

	public List<Long> getTokens() {
		for (Instrument instrument : getInstrumentMap().get(getParameterData().getExchange().get(ohlStrategyExchangeIndex))) {
			if ((StringUtils.isEmpty(ohlStrategyInstrumentType)
					|| instrument.getInstrument_type().equals(ohlStrategyInstrumentType))
					&& (org.apache.commons.lang3.StringUtils.isBlank(ohlStrategyExpiryMonth) 
					|| instrument.getTradingsymbol().contains(ohlStrategyExpiryMonth.toUpperCase()))
					&& !instrument.getTradingsymbol().contains(SYMBOL_HYPHEN)) {
				tokens.add(instrument.getInstrument_token());
			}
		}
		return tokens;
	}

	public void setTokens(List<Long> tokens) {
		this.tokens = tokens;
	}

	public Map<Long, String> getTradingSymbolMap() {
		return tradingSymbolMap;
	}

	public void setTradingSymbolMap(List<Instrument> instruments) {
		for (Instrument instrument : instruments) {
			tradingSymbolMap.put(instrument.getInstrument_token(), instrument.getTradingsymbol());
		}
	}

	public int getTickDisperserActorRoutees() {
		return tickDisperserActorRoutees;
	}

	public void setTickDisperserActorRoutees(int tickDisperserActorRoutees) {
		this.tickDisperserActorRoutees = tickDisperserActorRoutees;
	}

	public int getOhlStrategyActorRoutees() {
		return ohlStrategyActorRoutees;
	}

	public void setOhlStrategyActorRoutees(int ohlStrategyActorRoutees) {
		this.ohlStrategyActorRoutees = ohlStrategyActorRoutees;
	}

	public int getOrderActorRoutees() {
		return orderActorRoutees;
	}

	public void setOrderActorRoutees(int orderActorRoutees) {
		this.orderActorRoutees = orderActorRoutees;
	}
	
	public int getTradeableTickDataLoggingInitDelay() {
		return tradeableTickDataLoggingInitDelay;
	}

	public void setTradeableTickDataLoggingInitDelay(int tradeableTickDataLoggingInitDelay) {
		this.tradeableTickDataLoggingInitDelay = tradeableTickDataLoggingInitDelay;
	}

	public int getTradeableTickDataLoggingInterval() {
		return tradeableTickDataLoggingInterval;
	}

	public void setTradeableTickDataLoggingInterval(int tradeableTickDataLoggingInterval) {
		this.tradeableTickDataLoggingInterval = tradeableTickDataLoggingInterval;
	}

	public double getOhlTradeableNLC() {
		return ohlTradeableNLC;
	}

	public void setOhlTradeableNLC(double ohlTradeableNLC) {
		this.ohlTradeableNLC = ohlTradeableNLC;
	}

	public double getOhlTradeableNHC() {
		return ohlTradeableNHC;
	}

	public void setOhlTradeableNHC(double ohlTradeableNHC) {
		this.ohlTradeableNHC = ohlTradeableNHC;
	}

	public boolean isEnableOHLTsTbConstraint() {
		return enableOHLTsTbConstraint;
	}

	public void setEnableOHLTsTbConstraint(boolean enableOHLTsTbConstraint) {
		this.enableOHLTsTbConstraint = enableOHLTsTbConstraint;
	}

	public int getOhlTradeCount() {
		return ohlTradeCount;
	}

	public void setOhlTradeCount(int ohlTradeCount) {
		this.ohlTradeCount = ohlTradeCount;
	}

	public int getOhlTradeTypePerInstrument() {
		return ohlTradeTypePerInstrument;
	}

	public void setOhlTradeTypePerInstrument(int ohlTradeTypePerInstrument) {
		this.ohlTradeTypePerInstrument = ohlTradeTypePerInstrument;
	}

	public int getOhlTradeQtyPerInstrument() {
		return ohlTradeQtyPerInstrument;
	}

	public void setOhlTradeQtyPerInstrument(int ohlTradeQtyPerInstrument) {
		this.ohlTradeQtyPerInstrument = ohlTradeQtyPerInstrument;
	}

	public int getOhlTradeTxnPerInstrument() {
		return ohlTradeTxnPerInstrument;
	}

	public void setOhlTradeTxnPerInstrument(int ohlTradeTxnPerInstrument) {
		this.ohlTradeTxnPerInstrument = ohlTradeTxnPerInstrument;
	}

	public int getOhlTradeTotalTxn() {
		return ohlTradeTotalTxn;
	}

	public void setOhlTradeTotalTxn(int ohlTradeTotalTxn) {
		this.ohlTradeTotalTxn = ohlTradeTotalTxn;
	}

}
