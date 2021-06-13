package com.scalablecapital.currencyservice.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.scalablecapital.currencyservice.services.FXService;

import org.apache.commons.lang3.builder.*;

public class Quote extends ExchangeReferenceRate {
    private BigDecimal amount;
    private BigDecimal converted;

    public Quote(String from, String to, BigDecimal amount, BigDecimal converted, BigDecimal rate) {
        super(from, to, rate.setScale(FXService.DEFAULT_SCALE, RoundingMode.HALF_EVEN));
        this.amount = amount;
        this.converted = converted.setScale(FXService.DEFAULT_SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getConvertedValue() {
        return converted;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Quote)) {
            return false;
        }

        Quote obj = (Quote)o;

        return new EqualsBuilder()
                .append(from, obj.from)
                .append(to, obj.to)
                .append(amount, obj.amount)
                .append(converted, obj.converted)
                .append(rate, obj.rate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(from)
            .append(to)
            .append(amount)
            .append(converted)
            .append(rate)
            .toHashCode();
    }
}
