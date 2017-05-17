package com.zanthrash.controllers

import com.zanthrash.services.RedskyService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@Slf4j
@RestController
@RequestMapping(value = "/product")
class ProductController {

  @Autowired
  RedskyService redskyService

  @RequestMapping(value = "/", method = RequestMethod.GET)
  def getBase() {
    return "boom"
  }


  @RequestMapping(value = '/{productId}', method = RequestMethod.GET)
  def getProductById(@PathVariable('productId') String productId) {
    def product = redskyService.getByProductId(productId)
    log.warn("Product ${product}")

    product
  }

}
