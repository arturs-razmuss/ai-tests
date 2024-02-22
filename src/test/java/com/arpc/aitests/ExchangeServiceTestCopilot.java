package com.arpc.aitests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExchangeServiceTestCopilot {

    @Mock
    private ExchangeRateProvider provider;

    private ExchangeService exchangeService;

    private CurrencyUnit validSourceCurrency;
    private CurrencyUnit validTargetCurrency;
    private ExchangeRate mockExchangeRate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exchangeService = new ExchangeService(provider);

        validSourceCurrency = Monetary.getCurrency("USD");
        validTargetCurrency = Monetary.getCurrency("EUR");
        mockExchangeRate = createMockExchangeRate();

        when(provider.getExchangeRate(validSourceCurrency, validTargetCurrency)).thenReturn(mockExchangeRate);
    }

    @Test
    void shouldReturnExchangeRateWhenValidCurrenciesAreProvided() {
        Optional<ExchangeRate> result = exchangeService.getExchangeRate(validSourceCurrency, validTargetCurrency);

        assertThat(result)
                .isPresent()
                .hasValue(mockExchangeRate);
    }

    @Test
    void shouldReturnEmptyOptionalWhenSourceCurrencyIsInvalid() {
        CurrencyUnit invalidCurrency = Monetary.getCurrency("INVALID");

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(invalidCurrency, validTargetCurrency);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalWhenTargetCurrencyIsInvalid() {
        CurrencyUnit invalidCurrency = Monetary.getCurrency("INVALID");

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(validSourceCurrency, invalidCurrency);

        assertThat(result).isEmpty();
    }

    private ExchangeRate createMockExchangeRate() {
        return ExchangeRate.of(validSourceCurrency, validTargetCurrency, 1.0);
    }
}