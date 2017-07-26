package com.juststocks.tradebot.bean.response.kiteconnect;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParameterData {

	@SerializedName("order_variety")
	@Expose
	private List<String> orderVariety = null;
	@SerializedName("segment")
	@Expose
	private List<String> segment = null;
	@SerializedName("transaction_type")
	@Expose
	private List<String> transactionType = null;
	@SerializedName("order_type")
	@Expose
	private List<String> orderType = null;
	@SerializedName("position_type")
	@Expose
	private List<String> positionType = null;
	@SerializedName("validity")
	@Expose
	private List<String> validity = null;
	@SerializedName("product")
	@Expose
	private List<String> product = null;
	@SerializedName("exchange")
	@Expose
	private List<String> exchange = null;

	public List<String> getOrderVariety() {
		return orderVariety;
	}

	public void setOrderVariety(List<String> orderVariety) {
		this.orderVariety = orderVariety;
	}

	public List<String> getSegment() {
		return segment;
	}

	public void setSegment(List<String> segment) {
		this.segment = segment;
	}

	public List<String> getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(List<String> transactionType) {
		this.transactionType = transactionType;
	}

	public List<String> getOrderType() {
		return orderType;
	}

	public void setOrderType(List<String> orderType) {
		this.orderType = orderType;
	}

	public List<String> getPositionType() {
		return positionType;
	}

	public void setPositionType(List<String> positionType) {
		this.positionType = positionType;
	}

	public List<String> getValidity() {
		return validity;
	}

	public void setValidity(List<String> validity) {
		this.validity = validity;
	}

	public List<String> getProduct() {
		return product;
	}

	public void setProduct(List<String> product) {
		this.product = product;
	}

	public List<String> getExchange() {
		return exchange;
	}

	public void setExchange(List<String> exchange) {
		this.exchange = exchange;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
