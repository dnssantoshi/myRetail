package com.casestudy.myretail.service;

import com.casestudy.myretail.constants.MyRetailConstants;
import com.casestudy.myretail.dao.ProductPriceDao;
import com.casestudy.myretail.dto.Product;
import com.casestudy.myretail.dto.ProductPrice;
import com.casestudy.myretail.entity.ProductPrices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/*
 This class is responsible for aggregating the data from multiple sources and provide a unified response
 */
@Service
public class ProductAggregationService {

	@Autowired
	public ProductNameService productNameService;
	@Autowired
	public ProductPriceDao productPriceDao;
	
	public Product getProduct(Long productId) throws Exception {
		JSONObject jsonObject = productNameService.getProductName(productId);
		String productName = jsonObject.getJSONObject(MyRetailConstants.PRODUCT).getJSONObject(MyRetailConstants.ITEM).getJSONObject(MyRetailConstants.PRODUCT_DESCRIPTION).getString(MyRetailConstants.TITLE);
		ProductPrices productPrice = productPriceDao.getProductPrice(productId);
		return populateProduct(productId, productPrice,productName);
	}
	
	public boolean updateProductPrice(Long productId, BigDecimal price) {
		return productPriceDao.updateProductPrice(productId, price);
	}

	private Product populateProduct(Long productId, ProductPrices productPrice, String productName) {
		Product product = new Product();
		product.setProductName(productName);
		product.setProductId(productId);

		ProductPrice price = new ProductPrice();
		if (productPrice == null) {
			price.setCurrency(null);
			price.setPrice(null);
		} else {
			price.setCurrency(productPrice.getCurrency());
			price.setPrice(productPrice.getPrice());
		}
		product.setProductPrice(price);
		return product;
	}

}
