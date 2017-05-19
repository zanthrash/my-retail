# myRetail

## Scope

Create a RESTful interface to the Redsky backend service and a local Redis database that holds the product prices.



## Getting Started

### 1. Clone the repo

```
git clone https://github.com/zanthrash/my-repo.git
```



### 2. Build and Run the project with Gradle

**NOTE: The needs to run against Java 1.8**


```
$ ./gradlew 
```

#### Install and Run Redis

Once redis is installed run from the proects root directory:
```
$ redis-server ./config/redis.conf
```

This will start up redis in appendonly mode to keep data around between restarts

#### Exercising the Service


Once the app is running:

API documentation via Swagger [http://localhost:8080](http://localhost:8080)

Get product by id: [http://localhost:8080/product/13860428/](http://localhost:8080/product/13860428/)
    
Get product price: [http://localhost:8080/product/13860428/price](http://localhost:8080/product/13860428/price)
    
Prices can be updated by making a POST call to [http://localhost:8080/product/13860428/price](http://localhost:8080/product/13860428/price)
with a JSON payload:
```
{
  "id": "12345",
  "value": 22.99,
  "currency_code": "USD"
}
```

A price can also be removed from the system by making a DELETE call to: [http://localhost:8080/product/13860428/price](http://localhost:8080/product/13860428/price)


    
## Reports

### Unit Tests

Running the **test** task will generate a test report located at **{projectDir}/build/reports/tests/index.html**


## Technologies Used

- Spring Boot (http://projects.spring.io/spring-boot)
- Spring Data (http://projects.spring.io/spring-data/)
- Retrofit (http://square.github.io/retrofit/)
- Spock (https://github.com/spockframework/spock)
- Redis (https://redis.io/)
- Swagger (http://swagger.io/)

