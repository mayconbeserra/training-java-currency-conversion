package com.scalablecapital.currencyservice.controllers;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import com.scalablecapital.currencyservice.common.Configuration;
import com.scalablecapital.currencyservice.models.Quote;
import com.scalablecapital.currencyservice.services.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api")
@Validated
public class CurrencyConverterController {
    
    private FXService forexService;

    public CurrencyConverterController() throws MalformedURLException {
        List<CentralBankService> centralBankServices = new ArrayList<>();
        centralBankServices.add(new ECBService(new URL(Configuration.getURL())));
        forexService = new FXService(centralBankServices);
    }

    @GetMapping("/quotes")
    public ResponseEntity<Quote> getExchangeRatesBySourceTarget(@RequestParam String source, @RequestParam String target, @RequestParam(name = "amount") @Min(1) int amount) {
        var quote = forexService.convert(source.toUpperCase(), target.toUpperCase(), BigDecimal.valueOf(amount));

        if (quote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(quote, HttpStatus.OK);
    }

    @GetMapping("/exchange-rates")
    public ResponseEntity<Object> getExchangeRates(
        @RequestParam(value = "source") Optional<String> source,
        @RequestParam(value = "target") Optional<String> target) {
 
        if (source.isEmpty() || target.isEmpty()) {
            return new ResponseEntity<>(forexService.getRates(), HttpStatus.OK);
        }

        return new ResponseEntity<>(forexService.convert(source.get().toUpperCase(), target.get().toUpperCase(), BigDecimal.valueOf(1)), HttpStatus.OK);
    }
}
