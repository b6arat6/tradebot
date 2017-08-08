/**
 * 
 */
package com.juststocks.tradebot.constants;

/**
 * @author bharath_kandasamy
 *
 */
public interface TradebotConstants {
	public static final String LOG_METHOD_ENTRY = "Entered";
	public static final String LOG_METHOD_EXIT = "Exited";
	public static final String LOG_LOGIN_SUCCESS = "LoggedIn";
	public static final String LOG_AUTHENTICATION_SUCCESS = "Authenticated";
	public static final String LOG_SESSION_EXPIRED = "Session expired";
	public static final String LOG_PARAMETER_LOAD_SUCCESS = "Parameters loaded";
	public static final String LOG_EXCHANGE_INSTRUMENTS_GET_SUCCESS = "Instruments for exchange received";
	public static final String LOG_RESPONSE_PARAMETERS = "ParameterData={}";
	public static final String LOG_RESPONSE_EXCHANGE_INSTRUMENTS = "Exchange={}, Instruments={}";
	public static final String LOG_WEB_SOCKECT_CONNECTION_SUCCESS = "WebSocket connection established";
	public static final String LOG_WEB_SOCKECT_INIT_SUCCESS = "WebSocket connected";
	public static final String LOG_WEB_SOCKECT_DISCONNECTION = "WebSocket disconnected";
	public static final String LOG_INSTRUMENTS_SUBSCRIPTION_SUCCESS = "Instruments subscription success";
	public static final String LOG_INSTRUMENTS_SUBSCRIBED = "SubscribedInstruments, Size={}, Instruments={}";
	public static final String LOG_INSTRUMENTS_UNSUBSCRIPTION_SUCCESS = "Instruments subscription success";
	public static final String LOG_INSTRUMENTS_UNSUBSCRIBED = "UnsubscribedInstruments, Size={}, Instruments={}";
	
	public static final String BEAN_KITE_CLIENT_FACADE = "kiteConnectClientFacade";
	public static final String BEAN_APACHE_HTTP_UTIL = "apacheHttpUtil";
	public static final String BEAN_TRADEBOT_APPLICATION = "tradebotApplication";
	
	public static final int HTTP_STATUS_CODE_REDIRECT = 302;
	public static final String HTTP_HEADER_LOCATION = "Location";
	
	public static final String SYMBOL_QUESTION = "?";
	public static final String SYMBOL_AMPERSAND = "&";

	public static final String KITE_CONNECT_QUERY_PARAM_API_KEY = "api_key=";
	public static final String KITE_CONNECT_QUERY_PARAM_ACCESS_TOKEN = "access_token=";
	
	public static final String AKKA_ACTOR_SYSTEM = "TradebotAkkaActorSystem";
	public static final String AKKA_OHL_TRADE_STRATEGY_ACTOR = "OHLTradeStrategyActor";
	public static final String AKKA_ORDER_ACTOR = "OrderActor";

	public static final String TRADE_STRATEGY_OHL_OL = "O=H, S(-), I={}, L={}, O={}, H={}, LTP={}";
	public static final String TRADE_STRATEGY_OHL_OH = "O=L, B(+), I={}, L={}, O={}, H={}, LTP={}";
	public static final String TRADE_STRATEGY_OHL_OL_REMOVED = "Removed from OLMap, I={}";
	public static final String TRADE_STRATEGY_OHL_OH_REMOVED = "Removed from OHMap, I={}";
	
	public static final String OL_TICK_MAP = "olTickMap={}";
	public static final String OH_TICK_MAP = "ohTickMap={}";
	
	public static final String ORDER_TYPE_OHL_TRADE_STRATEGY = "OHLTradeStrategyOrder";
	
}
