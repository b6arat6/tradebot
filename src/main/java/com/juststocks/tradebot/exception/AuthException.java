/**
 * 
 */
package com.juststocks.tradebot.exception;

/**
 * @author bharath_kandasamy
 *
 */
public class AuthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111660557043752948L;
	
	public AuthException(String message) {
		super(message);
	}
	
	public AuthException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
