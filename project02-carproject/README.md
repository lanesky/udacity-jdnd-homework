# Car-Project

This is a project submission for [Udacity JDND project 2](https://github.com/udacity/JDND/tree/master/projects/P02-VehiclesAPI).

## Structure

- [Vehicles API](vehicles-api/README.md)
- [Pricing Service](pricing-service/README.md)
- [Boogle Maps](boogle-maps/README.md)
- [Eureka Server](eureka-server/README.md)

## Run the applications

1. $ mvn clean package (run this in each directory containing the separate applications)
2. Boogle Maps: $ java -jar target/boogle-maps-0.0.1-SNAPSHOT.jar
   - The service is available by default on port 9191. You can check it on the command line by using $ curl http://localhost:9191/maps\?lat\=20.0\&lon\=30.0
3. Eureka Server: $ java -jar target/eureka-server-0.0.1-SNAPSHOT.jar
   - The service is available by default on port 8761. 
4. Pricing Service: $ java -jar target/pricing-service-0.0.1-SNAPSHOT.jar
   - The service is available by default on port 8762 and is registered into the above Eureka Server. 
5. Vehicles API: $ java -jar target/vehicles-api-0.0.1-SNAPSHOT.jar
