package com.zanthrash.exceptions

class ProductNotFoundException extends RuntimeException{
  ProductNotFoundException(String productId) {
    super("Could not find a product with id: ${productId}".toString())
  }
}
