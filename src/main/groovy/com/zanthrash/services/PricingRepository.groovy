package com.zanthrash.services

import com.zanthrash.exceptions.PriceNotFoundException
import com.zanthrash.model.ProductPrice
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Slf4j
@Service
class PricingRepository {

  private final static KEY = "product_price"

  @Autowired
  private RedisTemplate<String, Object> redisTemplate

  def upsertProductPrice(ProductPrice productPrice) {
    String productPriceJson = JsonOutput.toJson(productPrice)
    log.warn("SET Product Price Json: ${productPriceJson}")

    redisTemplate
      .opsForValue()
      .set(constructKey(productPrice.id), productPriceJson)
  }

  ProductPrice getProductPrice(String productId) {
    String productPriceJson = redisTemplate
      .opsForValue()
      .get(constructKey(productId))

    if(productPriceJson) {

      Map productPriceMap = new JsonSlurper().parseText(productPriceJson)

      ProductPrice productPrice = new ProductPrice(productPriceMap)

      log.info("GET Product Price: ${productPrice}")

      return productPrice
    } else {
      throw new PriceNotFoundException(productId)
    }
  }

  def deletePrice(String productId) {
    redisTemplate
      .delete(constructKey(productId))
  }

  private constructKey(String productId) {
    "${KEY}:${productId}".toString()
  }

}