package com.arpc.aitests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import java.util.Optional;

import static com.arpc.aitests.ExchangeRateHelper.getExchangeRate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 2 hand written tests then rest generated with CodiumAI
 */
@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    private static final String USD_CODE = "USD";
    private static final String EUR_CODE = "EUR";
    private static final CurrencyUnit USD_CURRENCY = Monetary.getCurrency("USD");
    private static final CurrencyUnit EUR_CURRENCY = Monetary.getCurrency("EUR");

    @InjectMocks
    ExchangeService exchangeService;

    @Mock
    ExchangeRateProvider provider;

    @Test
    public void shouldRetrieveExchangeRateBetweenValidCurrencyUnits() {
        var exchangeRate = getExchangeRate(USD_CODE, EUR_CODE, "2");
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenReturn(exchangeRate);

        // Act
        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        // Assert
        assertThat(result).isPresent().contains(exchangeRate);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenExchangeRateNotFoundForCurrencyUnits() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(CurrencyConversionException.class);

        // Act
        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        // Assert
        assertThat(result).isEmpty();
    }


    // When invalid currency units are provided, the exchange rate should not be retrieved and an empty optional should be returned
    @Test
    public void shouldReturnEmptyOptionalWhenInvalidCurrencyUnitsProvided() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(UnknownCurrencyException.class);

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        assertThat(result).isEmpty();
    }

    // When an exception occurs while retrieving the exchange rate, an empty optional should be returned
    @Test
    public void shouldReturnEmptyOptionalWhenExceptionOccurs() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(CurrencyConversionException.class);

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        assertThat(result).isEmpty();
    }

    // Retrieving exchange rate between valid currency codes
    @Test
    public void shouldRetrieveExchangeRateBetweenValidCurrencyCodes() {
        ExchangeRate exchangeRate = ExchangeRateHelper.getExchangeRate(USD_CODE, EUR_CODE, "2");
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenReturn(exchangeRate);

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        assertThat(result).isPresent().contains(exchangeRate);
    }

    // Retrieving exchange rate with invalid base currency code
    @Test
    public void shouldRetrieveExchangeRateWithInvalidBaseCurrencyCode() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(UnknownCurrencyException.class);

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        assertThat(result).isEmpty();
    }

    // Retrieving exchange rate with invalid term currency code
    @Test
    public void shouldReturnEmptyOptionalWhenInvalidTermCurrencyCodeProvided() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(UnknownCurrencyException.class);

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        assertThat(result).isEmpty();
    }

    // Mocking ExchangeRateProvider to throw exception
    @Test
    public void shouldReturnEmptyOptionalWhenExchangeRateProviderThrowsException() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(CurrencyConversionException.class);

        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        assertThat(result).isEmpty();
    }





}