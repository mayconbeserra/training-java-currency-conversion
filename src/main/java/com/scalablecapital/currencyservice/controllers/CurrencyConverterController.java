package com.scalablecapital.currencyservice.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.scalablecapital.currencyservice.services.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConverterController {
    
    private FXService forexService;

    public CurrencyConverterController() throws MalformedURLException {
        List<CentralBankService> centralBankServices = new ArrayList<>();
        centralBankServices.add(new ECBService(new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml")));
        forexService = new FXService(centralBankServices);
    }

    @GetMapping("/exchange-rates/{amount}/")
    public String getURLStatusMessage(@RequestParam String url)
    {

        return url;
    }
}
