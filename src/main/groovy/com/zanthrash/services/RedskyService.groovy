package com.zanthrash.services

import retrofit.http.GET
import retrofit.http.Headers
import retrofit.http.Path


interface RedskyService {

  @Headers("Accept: application/json")
  @GET("/{productId}")
  Map getByProductId(@Path("productId") String productId);
}