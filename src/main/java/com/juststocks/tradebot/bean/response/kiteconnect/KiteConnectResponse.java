package com.juststocks.tradebot.bean.response.kiteconnect;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KiteConnectResponse<T> {

	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("data")
	@Expose
	private T data;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("error_type")
	@Expose
	private String errorType;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T kiteClientData) {
		this.data = kiteClientData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}