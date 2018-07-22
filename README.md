# Converto-Matic! ®

Converto-matic! ® allows you to get exchange-rates for many currencies. It also makes it easy for you to know the value of your money in different currencies!

### How to Run the REST WebService

Just run the following command in the project root: 
```
mvnw spring-boot:run
```
### How to Use the REST WebService

Then the following endpoints are available for exchange-rates and money convertions:

Get exchange rate from currency A to currency B:
```
GET http://[webserviceIP]:[webservicePort]/exchange?currA=EUR&currB=USD
```
Get all available exchange rates from currency A:
```
GET http://[webserviceIP]:[webservicePort]/exchange?currA=EUR
```
Get value convertion from currency A to a list of supplied currencies (maximum 10 currencies)
```
GET http://[webserviceIP]:[webservicePort]/convert?currA=EUR&currB=USD&currC=YEN&currD=FRC&currE=RUP&value=2 
```
### API Documentation

https://app.swaggerhub.com/apis/MigSeabra/Converto-Matic/1.0.0


