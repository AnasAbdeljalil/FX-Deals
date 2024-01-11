package com.progresssoft.fx.deals;

import java.util.List;

public class FxDealRequest {

	private String dealUniqueId;
	private List<String> fromCurrencies;
	private String toCurrency;
	private String dealTimestamp;
	private List<Double> dealAmmounts;

	public String getDealUniqueId() {
		return dealUniqueId;
	}

	public void setDealUniqueId(String dealUniqueId) {
		this.dealUniqueId = dealUniqueId;
	}

	public List<String> getFromCurrencies() {
		return fromCurrencies;
	}

	public void setFromCurrencies(List<String> fromCurrencies) {
		this.fromCurrencies = fromCurrencies;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public String getDealTimestamp() {
		return dealTimestamp;
	}

	public void setDealTimestamp(String dealTimestamp) {
		this.dealTimestamp = dealTimestamp;
	}

	public List<Double> getDealAmmounts() {
		return dealAmmounts;
	}

	public void setDealAmmounts(List<Double> dealAmmounts) {
		this.dealAmmounts = dealAmmounts;
	}

	@Override
	public String toString() {
		return "FxDealRequest [dealUniqueId=" + dealUniqueId + ", fromCurrencies=" + fromCurrencies + ", toCurrency="
				+ toCurrency + ", dealTimestamp=" + dealTimestamp + ", dealAmmounts=" + dealAmmounts + "]";
	}

}
