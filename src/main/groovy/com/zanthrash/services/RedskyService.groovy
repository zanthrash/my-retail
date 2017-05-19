package com.zanthrash.services

import com.zanthrash.model.Product
import com.zanthrash.model.RedskyResult
import retrofit.http.GET
import retrofit.http.Headers
import retrofit.http.Path

interface RedskyService {

  @Headers("Accept: application/json")
  @GET("/{productId}")
  RedskyResult getByProductId(@Path("productId") String productId);
}