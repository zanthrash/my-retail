package com.zanthrash.exceptions

import com.zanthrash.model.RedskyResult


class AttemptInsertPriceNotFoundException extends RuntimeException {

  RedskyResult result
  String productId

  public AttemptInsertPriceNotFoundException(String productId, RedskyResult result) {
    super("A price for the product with id: ${productId} cannot be found.")
    this.productId = productId
    this.result = result
  }
}
