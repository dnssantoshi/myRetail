package service

import com.casestudy.myretail.dao.ProductPriceDao
import com.casestudy.myretail.entity.ProductPrices
import com.casestudy.myretail.service.ProductAggregationService
import com.casestudy.myretail.service.ProductNameService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import spock.lang.Specification
import spock.lang.Subject

class ProductAggregationServiceSpec extends Specification {
    @Subject
    ProductAggregationService sut = new ProductAggregationService(
            productNameService: Mock(ProductNameService),
            productPriceDao: Mock(ProductPriceDao)
    )

    void 'services autowired correctly'() {
        expect:
        ProductAggregationService.getAnnotation(Service)
        ProductAggregationService.getDeclaredField('productNameService').getAnnotation(Autowired).required()
        ProductAggregationService.getDeclaredField('productPriceDao').getAnnotation(Autowired).required()
    }

    void 'get product details based on ProductId'() {
        when:
        def resp = sut.getProduct(1111111)

        then:
        1 * sut.productNameService.getProductName(1111111) >> new JSONObject('{"product":{"item":{"product_description":{"title":"some name"}}}}')
        1 * sut.productPriceDao.getProductPrice(1111111) >> new ProductPrices(productId: 1111111, price: 13.99, currency: 'USD')
        resp.productId == 1111111
        resp.productName == 'some name'
        resp.productPrice.price == 13.99
        resp.productPrice.currency == 'USD'
    }

    void 'update product price details'() {
        when:
        def resp = sut.updateProductPrice(122346, 4.99)

        then:
        1 * sut.productPriceDao.updateProductPrice(122346,4.99) >> true
        resp.toString() == 'true'
    }

}
