package com.arpc.aitests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryException;
import javax.money.UnknownCurrencyException;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRateProvider provider;

    public Optional<ExchangeRate> getExchangeRate(@NonNull CurrencyUnit sourceCurrency, @NonNull CurrencyUnit targetCurrency) {
        try {
            return Optional.ofNullable(provider.getExchangeRate(sourceCurrency, targetCurrency));
        } catch (MonetaryException e) {
            log.error("Exchange rate retrieval failed for {} to {}", sourceCurrency, targetCurrency, e);
            return Optional.empty();
        }
    }

    public Optional<ExchangeRate> getExchangeRate(@NonNull String sourceCurrency, @NonNull String targetCurrency) {
        try {
            CurrencyUnit source = Monetary.getCurrency(sourceCurrency);
            CurrencyUnit target = Monetary.getCurrency(targetCurrency);
            return getExchangeRate(source, target);
        } catch (UnknownCurrencyException e) {
            log.error("Invalid currency code provided for exchange rate retrieval", e);
            return Optional.empty();
        }
    }
}
