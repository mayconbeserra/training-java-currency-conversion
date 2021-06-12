package com.scalablecapital.currencyservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.scalablecapital.currencyservice.models.ExchangeReferenceRate;
import com.scalablecapital.currencyservice.models.Quote;
import com.scalablecapital.currencyservice.services.CentralBankService;
import com.scalablecapital.currencyservice.services.ECBException;
import com.scalablecapital.currencyservice.services.ECBService;
import com.scalablecapital.currencyservice.services.FXService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FXServiceTests {
    
    private static final String EUR = "EUR";
    private static final String USD = "USD";
    private static final String BRL = "BRL";
    private static final String HUF = "HUF";

    @Test
    public void whenCurrencyExists_then_should_convert() throws ECBException
    {
        // Arrange
        ECBService ecbService = mock(ECBService.class);
        when(ecbService.getDefaultCurrency()).thenReturn(EUR);

        List<CentralBankService> cbsServices = new ArrayList<>();
        cbsServices.add(ecbService);

        List<ExchangeReferenceRate> quotes = new ArrayList<>();
        quotes.add(new ExchangeReferenceRate(ecbService.getDefaultCurrency(), USD, CreateBigDecimal("1.21")));
        quotes.add(new ExchangeReferenceRate(ecbService.getDefaultCurrency(), BRL, CreateBigDecimal("7")));
        quotes.add(new ExchangeReferenceRate(ecbService.getDefaultCurrency(), HUF, CreateBigDecimal("100")));

        when(ecbService.getRates()).thenReturn(quotes);
        
        var amount = new BigDecimal(100);
        FXService service = new FXService(cbsServices);

        // Act
        var resultEURtoUSD = service.convert("EUR", "USD", amount);
        var resultUSDtoEUR = service.convert("USD", "EUR", amount);

        // Assert
        var expectedResultEURtoUSD = new Quote(
            EUR,
            USD,
            amount,
            CreateBigDecimal("121"),
            CreateBigDecimal("1.21")
        );

        var expectedResultUSDtoEUR= new Quote(
            USD,
            EUR,
            amount,
            CreateBigDecimal("82.6446"),
            CreateBigDecimal("0.8264")
        );

        assertEquals(expectedResultEURtoUSD, resultEURtoUSD);
        assertEquals(expectedResultUSDtoEUR, resultUSDtoEUR);
    }

    private BigDecimal CreateBigDecimal(String value) {
        return new BigDecimal(value).setScale(FXService.DEFAULT_SCALE);
    }
}
