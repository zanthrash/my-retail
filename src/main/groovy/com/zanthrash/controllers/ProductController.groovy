package com.zanthrash.controllers

import com.zanthrash.exceptions.AttemptInsertPriceNotFoundException
import com.zanthrash.exceptions.PriceNotFoundException
import com.zanthrash.exceptions.ProductNotFoundException
import com.zanthrash.model.ProductPrice
import com.zanthrash.model.RedskyResult
import com.zanthrash.services.PricingRepository
import com.zanthrash.services.RedskyService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import retrofit.RetrofitError

@Slf4j
@RestController
@RequestMapping(value = "/product")
class ProductController {

  @Autowired
  RedskyService redskyService

  @Autowired
  PricingRepository pricingRepository

  @RequestMapping(value = '/{productId}', method = RequestMethod.GET)
  def getProductById(@PathVariable('productId') String productId) {
    ProductPrice productPrice
    RedskyResult result

    try {
      result = redskyService.getByProductId(productId)
    } catch (RetrofitError e) {
      throw new ProductNotFoundException(productId)
    }

    if (!result) throw new ProductNotFoundException(productId)

    try {
      productPrice = pricingRepository.getProductPrice(productId)
    }
    catch (PriceNotFoundException ex) {
      throw new AttemptInsertPriceNotFoundException(productId, result)
    }

    if(productPrice && result) {
      return createProductResponse(result, productPrice)
    }
  }


  private createProductResponse(RedskyResult result, ProductPrice productPrice) {
    return [
      id: result.product?.item?.tcin,
      name: result.product?.item?.productDescription?.title,
      current_price: [
        value: productPrice.value,
        currency_code: productPrice.currency_code
      ]
    ]
  }


  @ResponseStatus(HttpStatus.CREATED)
  @ExceptionHandler(AttemptInsertPriceNotFoundException)
  Map attemptInsertPriceNotFoundHandler(AttemptInsertPriceNotFoundException e) {
    if(e.result) {
      ProductPrice productPrice = new ProductPrice(id: e.result.product.item.tcin, value: e.result.product.price.listPrice.price, currency_code: 'USD' )
      log.warn("Price not found in pricingRepository. Extracting from product info and saving.")
      pricingRepository.upsertProductPrice(productPrice)
      return createProductResponse(e.result, productPrice)
    } else {
      throw new PriceNotFoundException(e.productId)
    }
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(PriceNotFoundException)
  Map priceNotFoundHandler(PriceNotFoundException e) {
    return [message: e.message, status: HttpStatus.NOT_FOUND]
  }


  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ProductNotFoundException)
  Map productNotFoundHandler(ProductNotFoundException e) {
    return [message: e.message, status: HttpStatus.NOT_FOUND]
  }
}
