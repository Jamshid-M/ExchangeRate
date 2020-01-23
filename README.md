# ExchangeRate
Exchange Rate App which converts one currency to another


# Exchange Rate App

Currency Exchange Rate app which takes data from ```https://api.exchangeratesapi.io ```

## Process of converting

Converting process is completed with using custom Deserializer because of too many arguments in Response body. </br>
With this Deserializer we can avoid iterations while finding out the currency which user wants to see.
