# Retail API

### Running Application

	"Retail API" application is a spring boot application. Maven is used for building the project. 
	
	You can run the application with following command. 
	
```sh
mvn spring-boot:run
```

	Application will start on "http://localhost:8080"
	
	Application is configured with swagger (http://localhost:8080/swagger-ui.html) will help to see api
	
	example to hit api
	
	curl -X POST "http://localhost:8080/calculateNetAmount" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"customer\": { \"customerId\": \"100\", \"employedAt\": \"store\", \"joinedAt\": \"2019-06-24T21:07:06.005Z\" }, \"productList\": [ { \"price\": 1000, \"type\": \"NONGROCERY\" } ]}"

### Running Tests

	Tests are written on two classes - com.retail.RetailApiTestCustomerService & com.retail.RetailApiTestPurchaseService 
	
	You can run tests by following command.
	
	Jacoco plugin used for generating code coverage report which will available under target directory (target/site/index.html)

```sh
mvn clean test -Dtest=com.retail.RetailApiTestCustomerService,com.retail.RetailApiTestPurchaseService
```