package com.scalablecapital.currencyservice.controllers;

import static org.mockito.ArgumentMatchers.nullable;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.scalablecapital.currencyservice.models.ExchangeReferenceRate;
import com.scalablecapital.currencyservice.models.Quote;
import com.scalablecapital.currencyservice.services.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyConverterController {
    
    private FXService forexService;

    public CurrencyConverterController() throws MalformedURLException {
        List<CentralBankService> centralBankServices = new ArrayList<>();
        centralBankServices.add(new ECBService(new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml")));
        forexService = new FXService(centralBankServices);
    }

    @GetMapping("/quotes")
    public Object getExchangeRatesBySourceTarget(@RequestParam String source, @RequestParam String target) throws ECBException {
        // TODO VALIDATE
        if (source == null || target == null) {
            return forexService.getRates();
        }
        
        var quote = forexService.convert(source, target, BigDecimal.valueOf(1));
        return quote;
    }

    @GetMapping("/exchange-rates")
    public List<ExchangeReferenceRate> getExchangeRates() throws ECBException {
        var quote = forexService.getRates();
        return quote;
    }
}
