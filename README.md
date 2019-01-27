# Volcano Island Booking API
### Back-end Tech Challenge

## Solution
For this API it was used the following: 
- Spring Boot
- H2 Embebed DataBase
- Swagger 2.x
- JPA
- Jetty embebed server
- Maven 3.x
- Mockito 1.9.5

## Assumptions and Considerations

* A person can perform a booking indicating for how many persons the booking is. A limit of 10 persons by booking is setted.
* Only a person who perform a booking must enter his/her personal information. Email, first name and last name are required.
* Time Zones are not considered.
* The table called **rules** contains some configurable rules to be executed during the bookings transactions. The values of this table are obtained on the app initialization. A change in a rule´s value on database won´t be refreshed on the app, but it could be considered for the future as an enhacement to implement as well as the API services to change the rules. 

## Running the application

For running the application, execute **mvn spring-boot:run**
Once the application is running, you can enter to **http://localhost:8080/swagger-ui.html** to see the API or **http://localhost:8080/h2-console** to see H2 DB console

In swagger you can find the implemented services divided in two controllers, availabilities-controller and booking-controller.

* availabilities-controller contains the operations related to get availabilities for Volcano Island
  * GET /api/availabilities (Get Availabilities for next 30 days)
  * GET /api/availabilities/by-range (Get Availabilities for a range within 30 days)

* booking-controller contains the operations related to bookings for Volcano Island
  * POST /api/bookings (Create a booking)
  * PUT /api/bookings (Update a booking)
  * DELETE /api/bookings/{id} (Cancel a booking)
  * GET /api/bookings/{id} (Get a specific booking)

To enter to H2 DB console set:

- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:volcano
- User Name: sa
- Password: 

## Tests

For running the tests, execute **mvn test**

## Author
Jorge Luis Almirón


