package com.zanthrash.controllers

import com.zanthrash.exceptions.AttemptInsertPriceNotFoundException
import com.zanthrash.exceptions.PriceNotFoundException
import com.zanthrash.exceptions.ProductNotFoundException
import com.zanthrash.model.Item
import com.zanthrash.model.ListPrice
import com.zanthrash.model.Price
import com.zanthrash.model.Product
import com.zanthrash.model.ProductDescription
import com.zanthrash.model.ProductPrice
import com.zanthrash.model.RedskyResult
import com.zanthrash.services.PricingRepository
import com.zanthrash.services.RedskyService
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Subject


class ProductControllerSpec extends Specification {

  RedskyService redskyService = Mock()
  PricingRepository pricingRepository = Mock()

  @Subject
  controller = new ProductController(
    redskyService: redskyService,
    pricingRepository: pricingRepository
  )

  def "GET product by id: successfully get product and price"() {
    given:
    String productId = '1234'

    when:
    Map result = controller.getProductById(productId)

    then:
    1 * redskyService.getByProductId(productId) >> createTestRedskyResult(productId)
    1 * pricingRepository.getProductPrice(productId) >> createTestProductPrice(productId)
    result == [
      id: productId,
      name: "title",
      current_price: [
        value: 12.99,
        currency_code: "USD"
      ]
    ]
  }

  def "GET product by id: successfully get product but not price"() {
    given:
    String productId = '1234'

    when:
    Map result = controller.getProductById(productId)

    then:
    1 * redskyService.getByProductId(productId) >> createTestRedskyResult(productId)
    1 * pricingRepository.getProductPrice(productId) >> { String id -> throw new PriceNotFoundException(id) }
    AttemptInsertPriceNotFoundException ex = thrown()
    ex.message == "A price for the product with id: 1234 cannot be found."
  }

  def "GET product by id: product not found"() {
    given:
    String productId = '1234'

    when:
    Map result = controller.getProductById(productId)

    then:
    1 * redskyService.getByProductId(productId) >> null
    0 * pricingRepository.getProductPrice(productId) >> null
    ProductNotFoundException ex = thrown()
    ex.message == "Could not find a product with id: 1234"
  }


  def "price not found exception handler called"() {
    given:

    AttemptInsertPriceNotFoundException ex = new AttemptInsertPriceNotFoundException("1234", createTestRedskyResult("1234") )

    when:
    Map result = controller.attemptInsertPriceNotFoundHandler(ex)

    then: "should extract a product price from result and upsert it"
    1 * pricingRepository.upsertProductPrice(_ as ProductPrice)

    result == [id:"1234", name:"title", current_price:[value:12.99, currency_code:"USD"]]
  }

  def "product not found exception handler called"() {
    given:

    ProductNotFoundException ex = new ProductNotFoundException("1234")

    when:
    Map result = controller.productNotFoundHandler(ex)

    then:
    result.message == "Could not find a product with id: 1234"
    result.status == HttpStatus.NOT_FOUND
  }

  private RedskyResult createTestRedskyResult(String id, BigDecimal price = 12.99) {
    return new RedskyResult(
      product: new Product(
        price: new Price(
          listPrice: new ListPrice(price: price)
        ),
        item: new Item(
          tcin: id,
          productDescription: new ProductDescription(
            generalDescription: "generalDescription",
            title: "title"
          )
        )
      )
    )
  }

  private ProductPrice createTestProductPrice(String id, BigDecimal price = 12.99) {
    return new ProductPrice(
      id: id,
      value: price,
      currency_code: "USD"
    )
  }
}