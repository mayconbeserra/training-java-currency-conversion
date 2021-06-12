package com.scalablecapital.currencyservice.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import com.scalablecapital.currencyservice.models.*;

public class FXService {

    private List<CentralBankService> centralBankServices;
    private static final int DEFAULT_CBS = 0;
    public static final int DEFAULT_SCALE = 4;

    public FXService(List<CentralBankService> centralBankServices) {
        this.centralBankServices = centralBankServices;
    }

    public Quote convert(String from, String to, BigDecimal amount) throws ECBException
    {
        var cbsService = centralBankServices.get(DEFAULT_CBS);
        var rates = cbsService.getRates();

        if (from.equals(cbsService.getDefaultCurrency())) {
            var rate = getRateTo(rates, to);
            var convertedValue = amount.multiply(rate);
            return new Quote(from, to, amount, convertedValue, rate);
        }

        if (to.equals(cbsService.getDefaultCurrency())) {
            var rate = getRateFrom(rates, from);
            var rateConverted = BigDecimal.valueOf(1).divide(rate, DEFAULT_SCALE, RoundingMode.HALF_EVEN);
            var convertedValue = amount.divide(rate, DEFAULT_SCALE, RoundingMode.HALF_EVEN);
            return new Quote(from, to, amount, convertedValue, rateConverted);
        }

        BigDecimal rateFrom = getRateFrom(rates, from);
        BigDecimal rateTo = getRateTo(rates, to);
        BigDecimal rateConversion = amount.divide(rateFrom, DEFAULT_SCALE, RoundingMode.HALF_EVEN);
        BigDecimal result = rateTo.multiply(rateConversion);

        return new Quote(from, to, amount, result, rateConversion);
    }

    private BigDecimal getRateFrom(List<ExchangeReferenceRate> rates, String from) throws ECBException
    {
       Stream<ExchangeReferenceRate> stream = rates.stream();

       var quote = stream.filter(item -> item.getFrom().equals(from)).findFirst();

       if (!quote.isPresent()) {
           throw new ECBException("Could not find currency!");
       }

       return quote.get().getRate();
    }

    private BigDecimal getRateTo(List<ExchangeReferenceRate> rates, String to) throws ECBException
    {
        Stream<ExchangeReferenceRate> stream = rates.stream();

        var quote = stream.filter(item -> item.getTo().equals(to)).findFirst();

        if (!quote.isPresent()) {
            throw new ECBException("Could not find currency!");
        }

        return quote.get().getRate();
    }
}
