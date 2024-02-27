package com.arpc.aitests;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.convert.RateType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ExchangeServiceCopilotTest {

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
        Optional<ExchangeRate> result = exchangeService.getExchangeRate("INVALID", "USD");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalWhenTargetCurrencyIsInvalid() {
        Optional<ExchangeRate> result = exchangeService.getExchangeRate("EUR", "INVALID");

        assertThat(result).isEmpty();
    }

    private ExchangeRate createMockExchangeRate() {
        return new ExchangeRateBuilder("mock", RateType.REALTIME)
                .setBase(validSourceCurrency)
                .setFactor(new DefaultNumberValue(new BigDecimal(1.0)))
                .setTerm(validTargetCurrency)
                .build();
    }
}