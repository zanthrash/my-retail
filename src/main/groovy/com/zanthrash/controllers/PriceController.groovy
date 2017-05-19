package com.zanthrash.controllers

import com.zanthrash.exceptions.PriceNotFoundException
import com.zanthrash.model.ProductPrice
import com.zanthrash.services.PricingRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping(value = "/product/{id}/price")
class PriceController {

  @Autowired
  PricingRepository pricingRepository

  @PostMapping("/")
  def upsertProductPrice(@RequestBody ProductPrice productPrice) {
     pricingRepository.upsertProductPrice(productPrice)
  }

  @GetMapping("/")
  ProductPrice getProductPrice(@PathVariable String id) {
    ProductPrice productPrice = pricingRepository.getProductPrice(id)
    if (!productPrice) {
      throw new PriceNotFoundException(id)
    }
    productPrice
  }

  @DeleteMapping("/")
  def deletePrice(@PathVariable String id) {
    pricingRepository.deletePrice(id)
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(PriceNotFoundException)
  Map priceNotFoundHandler(PriceNotFoundException e) {
    return [message: e.message, status: HttpStatus.NOT_FOUND]
  }
}
