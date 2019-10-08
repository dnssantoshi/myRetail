package com.casestudy.myretail.dao;

import com.casestudy.myretail.entity.ProductPrices;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
/*
  This interface serves as a source for database operations - CRUD retrieval/update
 */
public interface ProductPriceDao extends CrudRepository<ProductPrices, String> {

	@Query("SELECT productId, price, currency FROM productprices WHERE productId=?0")
	ProductPrices getProductPrice(Long productId);

	@Query("UPDATE productprices SET price=?1 WHERE productId=?0 IF EXISTS")
	boolean updateProductPrice(Long productId, BigDecimal price);
}