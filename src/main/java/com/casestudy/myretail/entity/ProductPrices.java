package com.casestudy.myretail.entity;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.math.BigDecimal;

/*
 This class represents aggregated response. It consists of productId, price and currency as data fields.
 */
@Table(value = "productprices")
public class ProductPrices {
	@PrimaryKey
	private Long productId;
	private BigDecimal price;
	private String currency;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
