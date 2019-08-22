package com.github.dearrudam.currency;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CurrencyRateRegistryTest {

    public static final double CHF_TO_USD = 0.98062;
    public static final double USD_TO_CHF = CHF_TO_USD * (-1);
    public static final double BRL_TO_USD = 4.30;
    public static final double USD_TO_BRL = BRL_TO_USD * (-1);

    private CurrencyRateRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CurrencyRateRegistry();
        registry.addRate("CHF", "USD", CHF_TO_USD);
        registry.addRate("BRL", "USD", BRL_TO_USD);
    }

    @Test
    void addRateAndGetRate() {

        assertEquals(1.0, registry.rate("CHF", "CHF"));
        assertEquals(1.0, registry.rate("USD", "USD"));
        assertEquals(1.0, registry.rate("BRL", "BRL"));

        assertEquals(CHF_TO_USD, registry.rate("CHF", "USD"));
        assertEquals(USD_TO_CHF, registry.rate("USD", "CHF"));

        assertEquals(BRL_TO_USD, registry.rate("BRL", "USD"));
        assertEquals(USD_TO_BRL, registry.rate("USD", "BRL"));

    }
}
