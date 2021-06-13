# Scalable Capital - Currency Conversion API

## How to Run

To simplify the process for building and running the application, I opted to use the multi-stage build Reference: https://docs.docker.com/develop/develop-images/multistage-build.

How to build:
`docker build --tag scalable .`

How to run:
`docker run -p 8080:8080 scalable`

Access on:
`http://localhost:8080`

## Tech Stack

* Spring Boot
* Maven
* Mockito
* Docker
* Redis (Future release)

## APIs

The API used is based on Wise (Formely Transfer-Wise). Check it here: https://api-docs.transferwise.com/#exchange-rates-list.

The main capabilities of the API are:

* GET Exchange Rates
* GET Exchange Rates with source and Target
* GET Quotes

### Get Exchange-Rates

Provide a list of exchange rates based on a Central Bank.

In future, a new parameter can be provided via HTTP-Header to specify the central bank used as Reference.

* Request

`GET api/exchange-rates`

* Response

```.json
[
    {
        "from": "EUR",
        "to": "USD",
        "rate": 1.2125
    },
    {
        "from": "EUR",
        "to": "JPY",
        "rate": 132.88
    },
    {
        "from": "EUR",
        "to": "BGN",
        "rate": 1.9558
    }
]
```

### GET Exchange Rates with source and Target

* Request

`GET api/exchange-rates?source=EUR&target=USD`

* Fields

| Field (Query Param) | Description   | Format   |
|---------------------|---------------|----------|
| source              | Currency From | ISO 4217 |
| target              | Current To    | ISO 4217 |

* Response

```.json
{
    "from": "EUR",
    "to": "USD",
    "rate": 1.2125,
    "amount": 1,
    "convertedValue": 1.2125
}
```

### GET Quotes

* Request

`GET api/quotes?source=EUR&target=USD&amount=100`

* Fields

| Field (Query Param) | Description   | Format    |
|---------------------|---------------|-----------|
| source              | Currency From | ISO 4217  |
| target              | Current To    | ISO 4217  |
| amount              | Amount        | Value > 0 |

* Response

```.json
{
    "from": "EUR",
    "to": "USD",
    "rate": 1.2125,
    "amount": 1,
    "convertedValue": 1.2125
}
```

## Decisions made and trade-offs

### European Central Bank URL

I have checked the link sent in the task to make it easy, and there was one XML URL too. Therefore,  I decided to use it instead of the HTML page.

To speed up the development,

the FXService expects a list of central bank service. The idea there was to make it return the best exchange rates in all the central bank available.

### BigDecimal type

Working with currencies is challenging.

I opted to use BigDecimal because it provides complete control over the precision and rounding of a number.

The current implementation has a default scale of 4, but I'd need to check with experts the best scale for this problem domain.

### Lack of caching (Not implemented)

Hitting the ECB website all the time is not the best approach.

For this purpose, I'd like to have implemented a caching mechanism.

The first request could populate the cache, and in the following requests, it should use the data from the cache.

> The cache service should be injected in any CentralBankService. A DI container should be used for this purpose.

To invalidate the cache, we'd need to address a few questions when ECB change the rates. It's once a day, as stated on the website, but it can have some exceptions.

> Redis could be a good option for that.

### Distribution

Build, test and run are fundamental things for any software.

The usage of containers enables a consistent way of running our applications.

The chose option here is Docker with a multi-stage build.

### Tests

There are not so many unit tests implemented. However, I used TDD to understand the primary components/classes to be built.

If I had more time, I'd cover with more tests some alternative scenarios.

## Next Steps

* Cover by Tests APIs
* Use a DI container
* Implement Caching
* Implement a feature to return the public website showing an interactive chart
