package com.scalablecapital.currencyservice.services;

import org.springframework.http.HttpStatus;

public class CurrencyNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String currency;
    private static final String ERROR_MESSAGE = "Currency (%s) was not found";
    
    public CurrencyNotFoundException(String currency) {
        super(String.format(ERROR_MESSAGE, currency));
        
        this.currency = currency;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public String getCurrency() {
        return this.currency;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
