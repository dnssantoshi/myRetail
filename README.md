Frameworks used:

- SpringBoot REST - Application development
- Spock - Integration testing
- Cassandra - Datastore
- Postman - Functional testing
- Maven - Build tool
- Tomcat - server 

Build & Deploy:

1. Execute /src/main/resources/cql/cql_initial_load.cql to create the keyspace and perform an initial data load
2. Run maven clean install to generate the updated jar file.
3. Run the /target/myretail-1.0.0.jar

Testing:

1. Please find the test cases under /src/test/groovy folder.
2. Postman : 
 (i) HttpMethod: 'GET'
     Url: 'https:localhost:8080/myRetail/products/13860428'
(ii) HttpMethod: 'PUT'
     Url: 'https:localhost:8080/myRetail/products/13860428'
     `Sample RequestBody - 
     {
     "productId": 13860428,
     "productName": "The Big Lebowski (Blu-ray)",
     "productPrice": {
     "currency": "USD",
     "price": 16.49 //this will updated as the new price 
     }
     }`

Database Configuration:
 1. 
Results:
- Will demonstrate a runnable instance of the implementation on my computer.