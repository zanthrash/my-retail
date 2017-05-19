package com.zanthrash.exceptions

import com.zanthrash.model.RedskyResult

class PriceNotFoundException extends RuntimeException{

  RedskyResult result

  public PriceNotFoundException(String productId) {
    super("A price for the product with id: ${productId} cannot be found.")
  }

  public PriceNotFoundException(String productId, RedskyResult result) {
    super("A price for the product with id: ${productId} cannot be found.")
    this.result = result
  }

}
