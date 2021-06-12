package com.scalablecapital.currencyservice.models;

import java.math.BigDecimal;

public class ExchangeReferenceRate {

    protected String from;
    protected String to;
    protected BigDecimal rate;

    public ExchangeReferenceRate(String from, String to, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
