/**
 * 
 */
package com.juststocks.tradebot.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.juststocks.tradebot.bean.response.kiteconnect.ParameterData;
import com.juststocks.tradebot.constants.TradebotConstants;
import com.rainmatter.models.Instrument;


/**
 * @author bharath_kandasamy
 *
 */
@ConfigurationProperties("com.juststocks.tradebot.zerodha")
public class ZerodhaProperties implements TradebotConstants {
	
	public static Map<Long, OLTick> olTickMap = new ConcurrentHashMap<>();
	public static Map<Long, OHTick> ohTickMap = new ConcurrentHashMap<>();
	
	public static Set<Long> nonOHLTickSet = new HashSet<>();
	
	public static Map<Long, String> orderedTickMap = new LinkedHashMap<>();
	
	private String userId;
	
	private String apikey;
	
	private String rootApiEndpoint;
	
	private String loginEndpoint;
	
	private String apiSecret;
	
	private String requestToken;
	
	private String accessToken;
	
	private String publicToken;
	
	private String parameterApiPath;
	
	private int ohlStrategyExchangeValueIndex;
	
	private String ohlStrategyInstrumentType;
	
	private String ohlStrategyExpiryMonth;
	
	private int ohlStrategyMode;
	
	private Map<String, String> authUriMap;
	
	private ParameterData parameterData;
	
	private Map<String, List<Instrument>> exchangeInstrumentMap = new HashMap<>();

	private Map<String, List<Instrument>> localInstrumentMap = new HashMap<>();
	
	private Map<Long, Instrument> tradingInstrumentMap = new HashMap<>();
	
	private Map<String, List<Instrument>> instrumentMap = new HashMap<>();
	
	private List<Long> tradingTokens = new ArrayList<>();
	
	private int tickDispenserActorRoutees;
	
	private int ohlStrategyActorRoutees;
	
	private int orderActorRoutees;
	
	private int tradeableTickDataLoggingInitDelay;
	
	private int tradeableTickDataLoggingInterval;
	
	private boolean ohlStrategyTradeEnabled;
	
	private double ohlTradeMinInstrumentPrice;
	
	private double ohlTradeMaxInstrumentPrice;
	
	private double ohlTradeableNLC;
	
	private double ohlTradeableNHC;
	
	private boolean ohlTotalBuySellConstraintEnabled;
	
	private int ohlOLTradeCount;
	
	private int ohlOHTradeCount;
	
	private int ohlTradeType;
	
	private int ohlTradeQtyPerInstrument;
	
	private int ohlTradeValuePerInstrument;
	
	private int ohlTradeTotalValue;
	
	private int ohlTradeOrderTypeValueIndex;
	
	private int ohlTradeOrderVarietyValueIndex;
	
	private double ohlTradeBOExtraStoploss;
	
	private double ohlTradeBOTargetPercent;
	
	private double ohlTradeBOTrailingStoploss;
	
	private boolean onlineTestingEnabled;
	
	private boolean offlineTestingEnabled;
	
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

	public int getOhlStrategyExchangeValueIndex() {
		return ohlStrategyExchangeValueIndex;
	}

	public void setOhlStrategyExchangeValueIndex(int ohlStrategyExchangeIndex) {
		this.ohlStrategyExchangeValueIndex = ohlStrategyExchangeIndex;
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

	public int getOhlStrategyMode() {
		return ohlStrategyMode;
	}

	public void setOhlStrategyMode(int ohlStrategyMode) {
		this.ohlStrategyMode = ohlStrategyMode;
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

	public Map<String, List<Instrument>> getExchangeInstrumentMap() {
		return exchangeInstrumentMap;
	}

	public void setExchangeInstrumentMap(String exchange, List<Instrument> instruments) {
		exchangeInstrumentMap.put(exchange, instruments);
//		setTradingTokens();
//		setTradingInstrumentMap();
	}
	
	public Map<String, List<Instrument>> getLocalInstrumentMap() {
		return localInstrumentMap;
	}
	
	public void setLocalInstrumentMap(String exchange, List<Instrument> instruments) {
		localInstrumentMap.put(exchange, instruments);
	}

	public List<Long> getTradingTokens() {
		return tradingTokens;
	}
	
	public Map<Long, Instrument> getTradingInstrumentMap() {
		return tradingInstrumentMap;
	}

	public void setTradingInstrumentMap(Map<String, List<Instrument>> instrumentMap) {
		for (Instrument instrument : instrumentMap.get(getParameterData().getExchange().get(ohlStrategyExchangeValueIndex))) {
			if (instrument.getInstrument_type().equals(ohlStrategyInstrumentType)
					&& (org.apache.commons.lang3.StringUtils.isBlank(ohlStrategyExpiryMonth) 
					|| instrument.getTradingsymbol().contains(ohlStrategyExpiryMonth.toUpperCase()))
					&& !instrument.getTradingsymbol().contains(SYMBOL_HYPHEN)) {
				this.tradingInstrumentMap.put(instrument.getInstrument_token(), instrument);
			}
		}
	}

	public void setTradingTokens() {
		for (Instrument instrument : getExchangeInstrumentMap().get(getParameterData().getExchange().get(ohlStrategyExchangeValueIndex))) {
			if (instrument.getInstrument_type().equals(ohlStrategyInstrumentType)
					&& (org.apache.commons.lang3.StringUtils.isBlank(ohlStrategyExpiryMonth) 
					|| instrument.getTradingsymbol().contains(ohlStrategyExpiryMonth.toUpperCase()))
					&& !instrument.getTradingsymbol().contains(SYMBOL_HYPHEN)) {
				this.tradingTokens.add(instrument.getInstrument_token());
			}
		}
	}

	public int getTickDispenserActorRoutees() {
		return tickDispenserActorRoutees;
	}

	public void setTickDispenserActorRoutees(int tickDispenserActorRoutees) {
		this.tickDispenserActorRoutees = tickDispenserActorRoutees;
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

	public boolean isOhlStrategyTradeEnabled() {
		return ohlStrategyTradeEnabled;
	}

	public void setOhlStrategyTradeEnabled(boolean ohlStrategyTradeEnabled) {
		this.ohlStrategyTradeEnabled = ohlStrategyTradeEnabled;
	}

	public double getOhlTradeMinInstrumentPrice() {
		return ohlTradeMinInstrumentPrice;
	}

	public void setOhlTradeMinInstrumentPrice(double ohlTradeMinInstrumentPrice) {
		this.ohlTradeMinInstrumentPrice = ohlTradeMinInstrumentPrice;
	}

	public double getOhlTradeMaxInstrumentPrice() {
		return ohlTradeMaxInstrumentPrice;
	}

	public void setOhlTradeMaxInstrumentPrice(double ohlTradeMaxInstrumentPrice) {
		this.ohlTradeMaxInstrumentPrice = ohlTradeMaxInstrumentPrice;
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

	public boolean isOhlTotalBuySellConstraintEnabled() {
		return ohlTotalBuySellConstraintEnabled;
	}

	public void setOhlTotalBuySellConstraintEnabled(boolean ohlTotalBuySellConstraintEnabled) {
		OHLTick.totalBuySellConstraintEnabled = ohlTotalBuySellConstraintEnabled;
		this.ohlTotalBuySellConstraintEnabled = ohlTotalBuySellConstraintEnabled;
	}

	public int getOhlOLTradeCount() {
		return ohlOLTradeCount;
	}

	public void setOhlOLTradeCount(int ohlOLTradeCount) {
		this.ohlOLTradeCount = ohlOLTradeCount;
	}

	public int getOhlOHTradeCount() {
		return ohlOHTradeCount;
	}

	public void setOhlOHTradeCount(int ohlOHTradeCount) {
		this.ohlOHTradeCount = ohlOHTradeCount;
	}

	public int getOhlTradeType() {
		return ohlTradeType;
	}

	public void setOhlTradeType(int ohlTradeTypePerInstrument) {
		this.ohlTradeType = ohlTradeTypePerInstrument;
	}

	public int getOhlTradeQtyPerInstrument() {
		return ohlTradeQtyPerInstrument;
	}

	public void setOhlTradeQtyPerInstrument(int ohlTradeQtyPerInstrument) {
		this.ohlTradeQtyPerInstrument = ohlTradeQtyPerInstrument;
	}

	public int getOhlTradeValuePerInstrument() {
		return ohlTradeValuePerInstrument;
	}

	public void setOhlTradeValuePerInstrument(int ohlTradeTxnPerInstrument) {
		this.ohlTradeValuePerInstrument = ohlTradeTxnPerInstrument;
	}

	public int getOhlTradeTotalValue() {
		return ohlTradeTotalValue;
	}

	public void setOhlTradeTotalValue(int ohlTradeTotalTxn) {
		this.ohlTradeTotalValue = ohlTradeTotalTxn;
	}
	
	public int getOhlTradeOrderTypeValueIndex() {
		return ohlTradeOrderTypeValueIndex;
	}

	public void setOhlTradeOrderTypeValueIndex(int ohlTradeOrderType) {
		this.ohlTradeOrderTypeValueIndex = ohlTradeOrderType;
	}
	
	public int getOhlTradeOrderVarietyValueIndex() {
		return ohlTradeOrderVarietyValueIndex;
	}

	public void setOhlTradeOrderVarietyValueIndex(int ohlTradeOrderVariety) {
		this.ohlTradeOrderVarietyValueIndex = ohlTradeOrderVariety;
	}

	public double getOhlTradeBOExtraStoploss() {
		return ohlTradeBOExtraStoploss;
	}

	public void setOhlTradeBOExtraStoploss(double ohlTradeBOStoploss) {
		this.ohlTradeBOExtraStoploss = ohlTradeBOStoploss;
	}

	public double getOhlTradeBOTargetPercent() {
		return ohlTradeBOTargetPercent;
	}

	public void setOhlTradeBOTargetPercent(double ohlTradeBOSquareoff) {
		this.ohlTradeBOTargetPercent = ohlTradeBOSquareoff;
	}

	public double getOhlTradeBOTrailingStoploss() {
		return ohlTradeBOTrailingStoploss;
	}

	public void setOhlTradeBOTrailingStoploss(double ohlTradeBOTrailingStoploss) {
		this.ohlTradeBOTrailingStoploss = ohlTradeBOTrailingStoploss;
	}

	public boolean isOnlineTestingEnabled() {
		return onlineTestingEnabled;
	}

	public void setOnlineTestingEnabled(boolean offlineTesting) {
		this.onlineTestingEnabled = offlineTesting;
	}

	public boolean isOfflineTestingEnabled() {
		return offlineTestingEnabled;
	}

	public void setOfflineTestingEnabled(boolean offlineTestingEnabled) {
		this.offlineTestingEnabled = offlineTestingEnabled;
	}

}
