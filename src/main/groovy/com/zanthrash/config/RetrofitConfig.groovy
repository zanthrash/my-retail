package com.zanthrash.config

import com.fasterxml.jackson.databind.ObjectMapper
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
//  @Bean
//  HttpLoggingInterceptor retrofitLogLevel(@Value('${retrofit.log.level:BODY}') String logLevel) {
//    def logging = new HttpLoggingInterceptor()
//    logging.setLevel(Level.valueOf(logLevel))
//    logging
//  }
//
//  @Autowired ObjectMapper objectMapper
//  @Autowired Call.Factory retrofitClient
//
  @Bean
  String redskyEndpoint() { // TODO: Externalize redsky url
    return "http://redsky.target.com/v2/pdp/tcin"
  }
//
//  @Bean
//  RedskyService redskyService(String redskyEndpoint) {
//    return new Retrofit.Builder()
//      .baseUrl(redskyEndpoint)
//      .callFactory(retrofitClient)
//      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//      .addConverterFactory(JacksonConverterFactory.create(objectMapper))
//      .build()
//      .create(RedskyService.class)
//  }

  int maxIdleConnections = 5
  int keepAliveDurationMs = 300000


  @Bean
  OkClient okClient() {
    OkHttpClient client = new OkHttpClient()
    client.setConnectTimeout(15000, TimeUnit.MILLISECONDS)
    client.setReadTimeout(20000, TimeUnit.MILLISECONDS)
    return new OkClient(client)
  }


  @Bean
  RedskyService redskyService(OkClient okHttpClient) {
    new RestAdapter.Builder()
      .setEndpoint(redskyEndpoint())
      .setClient(okHttpClient)
      .setConverter(new JacksonConverter())
      .setLogLevel(RestAdapter.LogLevel.BASIC)
      .setLog(new Slf4jRetrofitLogger(RedskyService.class))
      .build()
      .create(RedskyService.class)
  }


}
