package controller

import com.casestudy.myretail.controller.ProductController
import com.casestudy.myretail.dto.Product
import com.casestudy.myretail.dto.ProductPrice
import com.casestudy.myretail.service.ProductAggregationService
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Subject

import javax.ws.rs.core.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ProductControllerSpec extends Specification {

    @Subject
    ProductController sut = new ProductController(productAggregationService: Mock(ProductAggregationService))
    MockMvc mvc = standaloneSetup(sut).build()

    void "GET /myRetail/products/{productId}"() {
        when:
        def resp = mvc.perform(
                get("/myRetail/products/{productId}", 11111111)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().response
        def content = new JsonSlurper().parseText(resp.contentAsString)

        then:
        1 * sut.productAggregationService.getProduct(11111111) >> new Product(productId: 11111111, productName: 'some display name',
                productPrice: new ProductPrice('USD', 13.99))
        resp.status == 200
        content == [productId: 11111111, productName: 'some display name', productPrice: [currency: 'USD', price: 13.99]]

    }

    void "GET /myRetail/products/{productId} - invalid product Id"() {
        when:
        def resp = mvc.perform(
                get("/myRetail/products/{productId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        0 * sut.productAggregationService.getProduct(0) >> null
        resp.status == 404
    }

    void "GET /myRetail/products/{productId} - no data for  product Id"() {
        when:
        def resp = mvc.perform(
                get("/myRetail/products/{productId}", 2222222)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * sut.productAggregationService.getProduct(2222222) >> null
        resp.status == 200
    }

    void "PUT /myRetail/products/{productId} - update success"() {
        given:
        def payload = new Product(productId: 11111111,
                productPrice: new ProductPrice('USD', 13.99))
        ObjectMapper mapper = new ObjectMapper()
        String request = mapper.writeValueAsString(payload)

        when:
        def resp = mvc.perform(
                put("/myRetail/products/{productId}", 11111111)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn()
                .response

        then:
        1 * sut.productAggregationService.updateProductPrice(11111111, 13.99) >> true
        resp.status == 200
        resp.contentAsString == 'ProductId: 11111111 is updated successfully'
    }

    void "PUT /myRetail/products/{productId} - update unsucessful"() {
        given:
        def payload = new Product(productId: 2222222,
                productPrice: new ProductPrice('USD', 13.99))
        ObjectMapper mapper = new ObjectMapper()
        String request = mapper.writeValueAsString(payload)

        when:
        def resp = mvc.perform(
                put("/myRetail/products/{productId}", 2222222)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn()
                .response

        then:
        1 * sut.productAggregationService.updateProductPrice(2222222, 13.99) >> false
        resp.status == 200
        resp.contentAsString == 'ProductId: 2222222 not updated'
    }

    void "PUT /myRetail/products/{productId} - Invalid ProductId - update unsucessful"() {
        given:
        def payload = new Product(productId: 0,
                productPrice: new ProductPrice('USD', 9.99))
        ObjectMapper mapper = new ObjectMapper()
        String request = mapper.writeValueAsString(payload)

        when:
        def resp = mvc.perform(
                put("/myRetail/products/{productId}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn()
                .response

        then:
        0 * sut.productAggregationService.updateProductPrice(0, 9.99) >> false
        resp.status == 404
        resp.contentAsString == 'Invalid ProductId: 0'
    }

    void "PUT /myRetail/products/{productId} - Exception thrown"() {
        given:
        def payload = new Product(productId: 33333333,
                productPrice: new ProductPrice('USD', 9.99))
        ObjectMapper mapper = new ObjectMapper()
        String request = mapper.writeValueAsString(payload)

        when:
        def resp = mvc.perform(
                put("/myRetail/products/{productId}", 33333333)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn()
                .response

        then:
        1 * sut.productAggregationService.updateProductPrice(33333333, 9.99) >> { throw new Exception('some error') }
        resp.status == 500
        resp.contentAsString == 'Unexpected error occurred while updating the price for product Id: 33333333'
    }


}