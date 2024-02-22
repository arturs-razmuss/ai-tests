package com.arpc.aitests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import java.util.Optional;


@Configuration
public class ExchangeRateServiceTestDouble extends ExchangeService {

    private String rate = "2";

    public ExchangeRateServiceTestDouble() {
        super(null);
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
    @Override
    public Optional<ExchangeRate> getExchangeRate(@NonNull CurrencyUnit sourceCurrency, @NonNull CurrencyUnit targetCurrency) {
        return Optional.of(ExchangeRateHelper.getExchangeRate(sourceCurrency.getCurrencyCode(), sourceCurrency.getCurrencyCode(), rate));
    }

    @Bean
    @Primary
    public ExchangeService exchangeService() {
        return new ExchangeRateServiceTestDouble();
    }
}
