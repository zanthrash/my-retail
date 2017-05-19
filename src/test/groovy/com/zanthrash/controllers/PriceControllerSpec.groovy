package com.zanthrash.controllers

import com.zanthrash.exceptions.PriceNotFoundException
import com.zanthrash.model.ProductPrice
import com.zanthrash.services.PricingRepository
import spock.lang.Specification
import spock.lang.Subject

class PriceControllerSpec extends Specification {

  PricingRepository pricingRepository = Mock()

  @Subject
  priceController = new PriceController(
    pricingRepository: pricingRepository
  )


  def "GET a price by id calls the Pricing Repository and returns a price"() {
    given:
    String productId = '123456'

    when:
    ProductPrice productPrice = priceController.getProductPrice(productId)

    then:
    1 * pricingRepository.getProductPrice(productId) >> new ProductPrice(id: productId, value: 12.99, currency_code: 'USD')
    productPrice.id == productId

  }

  def "GETing a price that is not found should throw a PriceNotFoundException  "() {
    given:
    String productId = "badId"

    when:
    ProductPrice productPrice = priceController.getProductPrice(productId)

    then:
    1 * pricingRepository.getProductPrice(productId) >> null
    thrown PriceNotFoundException
  }

  def "upserting a price should call the Pricing Repo once"() {
    given:
    ProductPrice productPrice = new ProductPrice(id: "123", value: 12.22, currency_code: "USD")

    when:
    priceController.upsertProductPrice(productPrice)

    then:
    1 * pricingRepository.upsertProductPrice(productPrice)
  }

  def "deleting a price should call the Pricing Repo once"() {
    given:
    String productId = "1234"

    when:
    priceController.deletePrice(productId)

    then:
    1 * pricingRepository.deletePrice(productId)
  }

}