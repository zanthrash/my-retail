package com.zanthrash.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit.RestAdapter

class Slf4jRetrofitLogger implements RestAdapter.Log {
  private final Logger logger

  public Slf4jRetrofitLogger(Class type) {
    this(LoggerFactory.getLogger(type))
  }

  public Slf4jRetrofitLogger(Logger logger) {
    this.logger = logger
  }

  @Override
  void log(String message) {
    logger.info(message)
  }
}
