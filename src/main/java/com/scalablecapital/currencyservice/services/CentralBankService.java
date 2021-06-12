package com.scalablecapital.currencyservice.services;

import java.net.URL;
import java.util.List;

import com.scalablecapital.currencyservice.models.*;

public abstract class CentralBankService {
    protected URL url;

    protected CentralBankService(URL url) {
        this.url = url;
    }

    public abstract String getName();

    public abstract String getDefaultCurrency();

    public abstract List<ExchangeReferenceRate> getRates() throws ECBException;
    
}
