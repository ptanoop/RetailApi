# Retail API

### Code Explanation

Application built on models which represents the business entities 

```
Customer (customerId, employedAt , joinedAt) 
	   
	customerId 	- Primary id of the customer
	employedAt 	- Where customer employed (needs to check how he associate with store)
	joinedAt   	- Date when customer joined with the store

Product (productType, price)
	
	productType 	- Represents product is either Grocery or Non-Grocery
	price 	    	- Price of the product
	
PurchaseBill (customerId, productList)

        customerId 	- Primary id of the customer
	productList	- List of the products purchased
	
PurchaseBillNetAmount (netAmount)

        netAmount 	- Total amount calculated as bill
```

Application business implemented as two services
```
CustomerService

	isEmployee	- Checks whether customer is the employee of the store
	isAffiliate	- Checks whether customer is the affiliate of the store
	yearsAssociatedWith - Get how many years customer associated with the store
	
PurchaseBillService

	applyDiscount	- Apply discount on the bill 
	applyDiscountOnEveryHundred - Apply discount on every hundred dollar
	calculateNetAmountBill - To calculate net bill amount after applying discounts
	
```
Application Configuration
Following values we can set in resource configuration
```
# name of the store
retail.store.name=main_store
# affiliates of the store
retail.store.affiliates=store1,store2,store3

# discount in percentage
retail.store.discount.percentage.for.employee=30
retail.store.discount.percentage.for.affiliate=10
retail.store.discount.percentage.for.customer.more.than.two.years=5

# discount in amount(dollar)
retail.store.discount.amount.on.every.hundred.dollar.bill=5

# discount applied on products
retail.store.discount.on.grocery.product.flag=false
retail.store.discount.on.non.grocery.product.flag=true
```

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
	
	
Sample Response

	{"netAmount":950.0}

### Running Tests

Tests are written on two classes - com.retail.RetailApiTestCustomerService & com.retail.RetailApiTestPurchaseService 
	
You can run tests by following command.
	
Jacoco plugin used for generating code coverage report which will available under target directory (target/site/index.html)

```sh
mvn clean test -Dtest=com.retail.RetailApiTestCustomerService,com.retail.RetailApiTestPurchaseService
```
