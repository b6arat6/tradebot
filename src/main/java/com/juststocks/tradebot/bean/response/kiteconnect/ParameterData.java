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
	
	public static enum IndexEnum {
		ORDER_VARIETY_REGULAR(0), ORDER_VARIETY_AMO(1), ORDER_VARIETY_BO(2), ORDER_VARIETY_CO(3)
		, SEGMENT_EQUITY(0)
		, TRANSACTION_TYPE_BUY(0), TRANSACTION_TYPE_SELL(1)
		, ORDER_TYPE_MARKET(0), ORDER_TYPE_LIMIT(1), ORDER_TYPE_SL(2), ORDER_TYPE_SL_M(3)
		, POSITION_TYPE_DAY(0)
		, VALIDITY_DAY(0)
		, PRODUCT_NRML(0), PRODUCT_CNC(1), PRODUCT_CO(2), PRODUCT_BO(3)
		, EXCHANGE_NSE(0), EXCHANGE_BSE(1), EXCHANGE_NFO(2);
		
		int index;
		
		IndexEnum(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
	}

}
