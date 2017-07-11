/**
 * 
 */
package com.juststocks.tradebot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bharath_kandasamy
 *
 */
@ConfigurationProperties("com.juststocks.tradebot")
public class TradebotProperties {
	
	private String kcApikey;
	
	private String rootApiEndpoint;
	
	private String loginEndpoint;

	public String getKcApikey() {
		return kcApikey;
	}

	public void setKcApikey(String kcApikey) {
		this.kcApikey = kcApikey;
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

}
