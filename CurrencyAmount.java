package com.progresssoft.fx.deals;

public class CurrencyAmount {
	private String currency;
	private double amount;

	public CurrencyAmount(String currency, double amount) {
		this.currency = currency;
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public double getAmount() {
		return amount;
	}
}