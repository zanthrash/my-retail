package com.zanthrash.model

import com.fasterxml.jackson.annotation.JsonProperty

class Item {
  @JsonProperty("product_description")
  ProductDescription productDescription

  String tcin;
}
