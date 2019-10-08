package com.casestudy.myretail.controller;

import com.casestudy.myretail.dto.Product;
import com.casestudy.myretail.service.ProductAggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/*
 This class serves as a main work horse for defining all the REST endpoint resources
 */
@RestController
@RequestMapping("/myRetail")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductAggregationService productAggregationService;

    @GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable Long productId, HttpServletResponse response) {
        Product product = null;
        // Validate request
        if (productId == null || productId <= 0) {
            LOG.error("ProductId Invalid");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return product;
        }
        // Process Request for Get
        try {
            product = productAggregationService.getProduct(productId);
            if (product.getProductName() == null || product.getProductId() == null)
                throw new Exception("ProductId you are searching is not found");
        } catch (Exception e) {
            LOG.error("Error occurred while retrieving details for product: " + productId);
        }
        // Return get response
        return product;
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT)
    public String updateProductPriceInfo(@PathVariable("productId") Long productId, @RequestBody Product product, HttpServletResponse response) {
        // Validate Request
        if (productId == null || productId <= 0) {
            LOG.error("ProductId Invalid");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return "Invalid ProductId: "+productId;
        }
        // Process request for Update
        try {
            boolean updated = productAggregationService.updateProductPrice(productId, product.getProductPrice().getPrice());
            // Return success/failure response
            if (updated)
                return "ProductId: " + productId + " is updated successfully";
            else
                return "ProductId: " + productId + " not updated";
        } catch (Exception e) {
            LOG.error("Unexpected error occurred while updating the price for product Id: " + productId);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "Unexpected error occurred while updating the price for product Id: " + productId;
        }
    }
}
