package com.casestudy.myretail.dto;

import java.math.BigDecimal;

/*
 This class represents the data transfer object containing price and currency of the product.
 */
public class ProductPrice {
	private String currency;
	private BigDecimal price;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductPrice(){
	}

	public ProductPrice(String currency, BigDecimal price) {
		this.currency = currency;
		this.price = price;
	}

}
