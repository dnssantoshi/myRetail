package com.casestudy.myretail.dto;

/*
 This class represents the data transfer object(dto) containing productId, productName and productPrice information
 */
public class Product {
    private Long productId;
    private String productName;
    private ProductPrice productPrice;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public Product() {
    }

}
