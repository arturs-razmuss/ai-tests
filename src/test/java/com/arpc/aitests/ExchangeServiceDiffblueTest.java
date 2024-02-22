package com.arpc.aitests;

import org.javamoney.moneta.convert.IdentityRateProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;
import javax.money.convert.ExchangeRate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ExchangeServiceDiffblueTest {
    /**
     * Method under test: {@link ExchangeService#getExchangeRate(String, String)}
     */
    @Test
    void testGetExchangeRate() {
        Optional<ExchangeRate> actualExchangeRate = (new ExchangeService(new IdentityRateProvider())).getExchangeRate("GBP",
                "GBP");
        assertFalse(actualExchangeRate.get().isDerived());
        assertTrue(actualExchangeRate.isPresent());
    }

    /**
     * Method under test: {@link ExchangeService#getExchangeRate(String, String)}
     */
    @Test
    void testGetExchangeRate2() {
        assertFalse((new ExchangeService(new IdentityRateProvider())).getExchangeRate("foo", "foo").isPresent());
    }

    /**
     * Method under test: {@link ExchangeService#getExchangeRate(String, String)}
     */
    @Test
    void testGetExchangeRate3() {
        Optional<ExchangeRate> actualExchangeRate = (new ExchangeRateServiceTestDouble()).getExchangeRate("GBP", "GBP");
        assertFalse(actualExchangeRate.get().isDerived());
        assertTrue(actualExchangeRate.isPresent());
    }

    /**
     * Method under test: {@link ExchangeService#getExchangeRate(String, String)}
     */
    @Test
    void testGetExchangeRate4() {
        assertFalse(
                (new ExchangeService(new IdentityRateProvider())).getExchangeRate("GBP", "Target Currency").isPresent());
    }

    /**
     * Method under test:
     * {@link ExchangeService#getExchangeRate(CurrencyUnit, CurrencyUnit)}
     */
    @Test
    void testGetExchangeRate5() {
        IdentityRateProvider provider = mock(IdentityRateProvider.class);
        when(provider.getExchangeRate(Mockito.<CurrencyUnit>any(), Mockito.<CurrencyUnit>any())).thenReturn(null);
        Optional<ExchangeRate> actualExchangeRate = (new ExchangeService(provider)).getExchangeRate((CurrencyUnit) null,
                null);
        verify(provider).getExchangeRate(Mockito.<CurrencyUnit>any(), Mockito.<CurrencyUnit>any());
        assertFalse(actualExchangeRate.isPresent());
    }

    /**
     * Method under test:
     * {@link ExchangeService#getExchangeRate(CurrencyUnit, CurrencyUnit)}
     */
    @Test
    void testGetExchangeRate6() {
        IdentityRateProvider provider = mock(IdentityRateProvider.class);
        when(provider.getExchangeRate(Mockito.<CurrencyUnit>any(), Mockito.<CurrencyUnit>any()))
                .thenThrow(new MonetaryException("An error occurred"));
        Optional<ExchangeRate> actualExchangeRate = (new ExchangeService(provider)).getExchangeRate((CurrencyUnit) null,
                null);
        verify(provider).getExchangeRate(Mockito.<CurrencyUnit>any(), Mockito.<CurrencyUnit>any());
        assertFalse(actualExchangeRate.isPresent());
    }
}
