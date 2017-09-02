/**
 * 
 */
package com.juststocks.tradebot.constants;

/**
 * @author bharath_kandasamy
 *
 */
public interface TradebotConstants {
	
	public static final String LOGGER_MAIN = "mainLogger";
	public static final String LOGGER_STRATEGY = "strategyLogger";
	public static final String LOGGER_ORDER = "orderLogger";
	
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
	public static final String LOG_INSTRUMENT_UNSUBSCRIBING= "UnsubscribingInstrument={}, T={}";
	public static final String LOG_ORDER_TYPE_OHL_STRATEGY_ORDERS_TRIGGERED = "OrderActor for OHLStrategy triggered";
	
	public static final String BEAN_KITE_CLIENT_FACADE = "kiteConnectClientFacade";
	public static final String BEAN_APACHE_HTTP_UTIL = "apacheHttpUtil";
	public static final String BEAN_TRADEBOT_APPLICATION = "tradebotApplication";
	
	public static final int HTTP_STATUS_CODE_REDIRECT = 302;
	public static final String HTTP_HEADER_LOCATION = "Location";
	
	public static final String SYMBOL_QUESTION = "?";
	public static final String SYMBOL_AMPERSAND = "&";
	public static final String SYMBOL_HYPHEN = "-";

	public static final String KITE_CONNECT_QUERY_PARAM_API_KEY = "api_key=";
	public static final String KITE_CONNECT_QUERY_PARAM_ACCESS_TOKEN = "access_token=";
	
	public static final String AKKA_ACTOR_SYSTEM = "TradebotAkkaActorSystem";
	public static final String AKKA_OHL_STRATEGY_ACTOR_REF = "OHLStrategyActorRef";
	public static final String AKKA_TICK_DISPENSER_ACTOR_REF = "TickDispenserActorRef";
	public static final String AKKA_ORDER_ACTOR_REF = "OrderActorRef";

	public static final String STRATEGY_OHL_OL = "O=L, S(+), I={}, L={}, O={}, H={}, LTP={}, T={}";
	public static final String STRATEGY_OHL_OH = "O=H, B(-), I={}, L={}, O={}, H={}, LTP={}, T={}";
	public static final String STRATEGY_OHL_OL_REMOVED = "Removed from OLMap, I={}, L={}, O={}, H={}, LTP={}, T={}";
	public static final String STRATEGY_OHL_OH_REMOVED = "Removed from OHMap, I={}, L={}, O={}, H={}, LTP={}, T={}";
	public static final String STRATEGY_OHL_OL_UPDATED = "Updated from OLMap, I={}, L={}, O={}, H={}, LTP={}, T={}";
	public static final String STRATEGY_OHL_OH_UPDATED = "Updated from OHMap, I={}, L={}, O={}, H={}, LTP={}, T={}";
	public static final String OL_TICK_SET_SIZE = "OLTickSet, Size={}";
	public static final String OH_TICK_SET_SIZE = "OHTickSet, Size={}";
	public static final String NON_OHL_TICK_SET_SIZE = "NonOHLTickSet, Size={}";
	
	public static final String ORDER_TYPE_OHL_STRATEGY = "OHLStrategyOrder";
	
	public static final String CRON_ENTRY_OHL_STRATEGY_ORDERS = "30 30 0 * * 0-6";
	
}
