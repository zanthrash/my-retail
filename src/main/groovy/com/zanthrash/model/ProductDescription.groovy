package com.zanthrash.model

import com.fasterxml.jackson.annotation.JsonProperty


class ProductDescription {
  String title

  @JsonProperty("general_description")
  String generalDescription
}
