# tool-rental-code-challenge

This demo application creates a small tool rental agreement generator, with a sample set of tools to rent and associated
data. The unit tests showcase several possible scenarios, and the primary service method prints out the resulting agreement 
to the console in the specified format. A REST endpoint was included to provide an easy way of testing the application 
ad-hoc, with varying inputs allowable in a standard json payload.

# Usage
The project can be built with with `mvn install`.
The application can be started using an IDE of your choosing, or with the command `mvn spring-boot:run`.
Once the application has started, you can interact with the application on the default port of 8080, making requests
such as: 
`curl -H "Content-Type: application/json" -d '{"toolCode":"JAKR", "checkoutDate": "2024-07-18", "rentalDurationDays": 5, "discountPercentage": 20}' localhost:8080/rental-agreements`

The request object takes the following form:
```json
{
  "toolCode": "JAKR", 
  "checkoutDate": "2024-07-18", 
  "rentalDurationDays": 5, 
  "discountPercentage": 20
}
```

The Response takes the following form:
```json
{
  "toolCode":"JAKR",
  "toolType":"JACKHAMMER",
  "brand":"Rigid",
  "rentalDurationDays":5,
  "checkoutDate":"2024-07-18",
  "dueDate":"2024-07-23",
  "dailyRentalCharge":"$2.99",
  "chargeDays":3,
  "preDiscountCharge":"$8.97",
  "discountPercent":20,
  "discountAmount":"$1.79",
  "finalCharge":"$7.18"
}
```