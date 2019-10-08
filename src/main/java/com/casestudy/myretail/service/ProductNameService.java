package com.casestudy.myretail.service;

import com.casestudy.myretail.exception.ProductNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")

/*
 Retrieve product name from external API
 */
public class ProductNameService {

    @Value("${myRetail.product.byId.get.url}")
    public String productUrl;

    public RestTemplate restTemplate = new RestTemplate();

    public JSONObject getProductName(Long productId) throws Exception {

        String url = productUrl + productId;
        String responseEntity = null;
        try {
            responseEntity = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotFoundException("Resource unavailable for: " + url);
            }
            throw new Exception("unexpected error " + e.getLocalizedMessage());
        } catch (RestClientException e) {
            throw new Exception("unexpected response: " + e.getLocalizedMessage());
        } catch (Exception e){
            throw new Exception("unexpected error: " + e.getLocalizedMessage());
        }

        if (responseEntity == null) {
            throw new ProductNotFoundException("Product details unavailable for: " + url);
        }
        return new JSONObject(responseEntity);
    }

}
