package com.zanthrash.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.squareup.okhttp.OkHttpClient
import com.zanthrash.services.RedskyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit.RestAdapter
import retrofit.client.OkClient
import retrofit.converter.JacksonConverter

import java.util.concurrent.TimeUnit

@Configuration
class RetrofitConfig {

  @Autowired
  RedskyProperties redskyProperties

  @Bean
  String redskyEndpoint() {
    return redskyProperties.baseUrl
  }

  @Bean
  OkClient okClient() {
    OkHttpClient client = new OkHttpClient()
    client.setConnectTimeout(15000, TimeUnit.MILLISECONDS)
    client.setReadTimeout(20000, TimeUnit.MILLISECONDS)
    return new OkClient(client)
  }

  @Bean
  ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper()
    objectMapper
      .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
      .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
      .disable(SerializationFeature.WRAP_ROOT_VALUE)
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

  }

  @Bean
  RedskyService redskyService(OkClient okHttpClient, ObjectMapper objectMapper, String redskyEndpoint) {
    new RestAdapter.Builder()
      .setEndpoint(redskyEndpoint)
      .setClient(okHttpClient)
      .setConverter(new JacksonConverter(objectMapper))
      .setLogLevel(RestAdapter.LogLevel.BASIC)
      .setLog(new Slf4jRetrofitLogger(RedskyService.class))
      .build()
      .create(RedskyService.class)
  }


}
