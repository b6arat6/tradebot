/**
 * 
 */
package com.juststocks.tradebot;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.juststocks.tradebot.bean.KiteConnectProperties;
import com.juststocks.tradebot.constants.TradebotConstants;

/**
 * @author bharath_kandasamy
 *
 */
@SpringBootConfiguration
@EnableConfigurationProperties(KiteConnectProperties.class)
public class TradebotConfiguration implements TradebotConstants {
	
}
