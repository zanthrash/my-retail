package com.zanthrash

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@ComponentScan(["com.zanthrash"])
@EnableAsync
@Slf4j
@SpringBootApplication
class Application {

  static void main(String[] args) {
    SpringApplication.run Application, args

    log.info("############################################")
    log.info("Welcome to myRetail API")
    log.info("")
    log.info("To test the app goto:")
    log.info("http://localhost:8080/product/13860427")
    log.info("")
    log.info("API Documentation can be found at:")
    log.info("http://localhost:8080/")
    log.info("############################################")

  }
}
