package com.zanthrash.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import groovy.transform.ToString

@ToString
class ProductPrice {
  String id
  BigDecimal value
  String currency_code
}
