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
	public static final String LOGGER_TRADEABLE_TICK = "tradeableTickLogger";
	
	public static final String METHOD_ENTRY = "Entered";
	public static final String METHOD_EXIT = "Exited";
	public static final String LOGIN_SUCCESS = "LoggedIn";
	public static final String AUTHENTICATION_SUCCESS = "Authenticated";
	public static final String SESSION_EXPIRED = "Session expired";
	public static final String PARAMETER_LOAD_SUCCESS = "Parameters loaded";
	public static final String EXCHANGE_INSTRUMENTS_GET_SUCCESS = "Instruments for exchange received";
	public static final String RESPONSE_PARAMETERS = "ParameterData={}";
	public static final String RESPONSE_EXCHANGE_INSTRUMENTS = "Exchange={}, Instruments={}";
	public static final String WEB_SOCKECT_CONNECTION_SUCCESS = "WebSocket connection established";
	public static final String WEB_SOCKECT_INIT_SUCCESS = "WebSocket connected";
	public static final String WEB_SOCKECT_DISCONNECTION = "WebSocket disconnected";
	public static final String INSTRUMENTS_SUBSCRIPTION_SUCCESS = "Instruments subscription success";
	public static final String INSTRUMENTS_SUBSCRIBED = "SubscribedInstruments, Size={}, Instruments={}";
	public static final String INSTRUMENTS_UNSUBSCRIPTION_SUCCESS = "Instruments subscription success";
	public static final String KITE_ON_TICK_SIZE = "OnTickSize={}";
	public static final String INSTRUMENT_UNSUBSCRIBING= "UnsubscribingInstrument={}, T={}";
	public static final String TRADE_TYPE_OHL_STRATEGY_TRIGGERED = "Trade (OHLStrategy) triggered";
	public static final String SHUTTING_DOWN = "Shutting down...";
	public static final String SHUTDOWN_COMPLETE = "Shutdown complete!";
	
	public static final String REPLACE_HOLDER_CODE = "<CODE>";
	public static final String REPLACE_HOLDER_ERROR = "<ERROR>";
	public static final String EXCEPTION_AUTH = "Authentication failed! Code=<CODE>, Error=<ERROR>";
	
	public static final String BEAN_KITE_CLIENT_FACADE = "kiteConnectClientFacade";
	public static final String BEAN_APACHE_HTTP_UTIL = "apacheHttpUtil";
	public static final String BEAN_TRADEBOT_APPLICATION = "tradebotApplication";
	
	public static final int HTTP_STATUS_CODE_REDIRECT = 302;
	public static final String HTTP_HEADER_LOCATION = "Location";
	
	public static final String SYMBOL_NEW_LINE = "\n";
	public static final String SYMBOL_QUESTION = "?";
	public static final String SYMBOL_AMPERSAND = "&";
	public static final String SYMBOL_HYPHEN = "-";

	public static final String KITE_CONNECT_AT_PT = SYMBOL_NEW_LINE + "AT={}" + SYMBOL_NEW_LINE + "PT={}";
	public static final String KITE_CONNECT_QUERY_PARAM_API_KEY = "api_key=";
	public static final String KITE_CONNECT_QUERY_PARAM_ACCESS_TOKEN = "access_token=";
	
	public static final String AKKA_ACTOR_SYSTEM = "TradebotAkkaActorSystem";
	public static final String AKKA_OHL_STRATEGY_ACTOR_REF = "OHLStrategyActorRef";
	public static final String AKKA_TICK_DISPENSER_ACTOR_REF = "TickDispenserActorRef";
	public static final String AKKA_ORDER_ACTOR_REF = "OrderActorRef";
	public static final String AKKA_TRADEABLE_TICK_DATA_LOGGING_ACTOR_CANCELLABLE = "TradeableTickDataLoggingActorCancellable";

	public static final String STRATEGY_OHL_OL = "O=L, B(+), I={}, LTP={}, L={}, O={}, H={}, T={}";
	public static final String STRATEGY_OHL_OH = "O=H, S(-), I={}, LTP={}, L={}, O={}, H={}, T={}";
	public static final String STRATEGY_OHL_OL_REMOVED = "Removed from OLMap, I={}, LTP={}, L={}, O={}, H={}, T={}";
	public static final String STRATEGY_OHL_OH_REMOVED = "Removed from OHMap, I={}, LTP={}, L={}, O={}, H={}, T={}";
	public static final String STRATEGY_OHL_OL_UPDATED = "Updated OLMap, I={}, LTP={}, L={}, O={}, H={}, T={}";
	public static final String STRATEGY_OHL_OH_UPDATED = "Updated OHMap, I={}, LTP={}, L={}, O={}, H={}, T={}";
	public static final String OL_TICK_MAP_SIZE = "OLTickMap, Size={}";
	public static final String OH_TICK_MAP_SIZE = "OHTickMap, Size={}";
	public static final String NON_OHL_TICK_SET_SIZE = "NonOHLTickSet, Size={}";
	
	public static final String OHL_OL_TICK = "O=L, I={}, LTP={}, L={}, O={}, H={}, NLC={}, NHC={}, Tb>Ts={}, T={}";
	public static final String OHL_OH_TICK = "O=H, I={}, LTP={}, L={}, O={}, H={}, NLC={}, NHC={}, Ts>Tb={}, T={}";
	
	public static final String ACTOR_ORDER_MSG_TYPE_OHL_STRATEGY = "OHLStrategyOrder";
	public static final String ACTOR_TRADEABLE_MSG_TICK_DATA_LOGGING = "LogTradeableTickData";
	
	public static final String CRON_ENTRY_OHL_STRATEGY_ORDERS = "52 30 3 * * 0-6";
	
}
