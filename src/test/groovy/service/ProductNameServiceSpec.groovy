package service

import com.casestudy.myretail.service.ProductNameService
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject

class ProductNameServiceSpec extends Specification {


    @Subject
    def sut = new ProductNameService(
            restTemplate: Mock(RestTemplate),
            productUrl: '/products/'
    )

    def 'fetch product details - happy path'() {
        given:
        def expectedResponse = "some name"
        def responseEntity = '{"product":{"item":{"product_description":{"title":"some name"}}}}'

        when:
        def actualResponse = sut.getProductName(112233)

        then:
        actualResponse.product.item.product_description.title == expectedResponse

        and:
        1 * sut.restTemplate.getForObject('/products/112233',String.class)  >> responseEntity
    }

    def 'fetch product details - empty response'() {
        given:
        def responseEntity = '{}'

        when:
        def actualResponse = sut.getProductName(112233)

        then:
        actualResponse.toString() == responseEntity

        and:
        1 * sut.restTemplate.getForObject('/products/112233',String.class)  >> responseEntity
    }
}
