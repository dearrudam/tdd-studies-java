package com.github.dearrudam;

import java.util.Currency;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class CurrencyExchange {

    private final HashMap<String, HashMap<String, Supplier<Double>>> rates = new HashMap<>();

    private static Double defaultRateSupplier() {
        return 0.0;
    }

    private static Double defaultSameRateSupplier() {
        return 1.0;
    }

    public CurrencyExchange addRate(Currency from, Currency to, Supplier<Double> rateFunction) {
        return this.addRate(from.getCurrencyCode(), to.getCurrencyCode(), rateFunction);
    }

    public CurrencyExchange addRate(String fromCurrencyCode, String toCurrencyCode, Supplier<Double> rateFunction) {
        this.rates
                .computeIfAbsent(fromCurrencyCode, (k) -> new HashMap<>())
                .compute(toCurrencyCode, (k, func) -> Optional
                        .ofNullable(rateFunction)
                        .orElseGet(() -> CurrencyExchange::defaultRateSupplier));
        return this;
    }

    public Supplier<Double> rateFor(String fromCurrency, Currency to) {
        return rateFor(fromCurrency, to.getCurrencyCode());
    }

    public Supplier<Double> rateFor(Currency from, String toCurrencyCode) {
        return rateFor(from.getCurrencyCode(), toCurrencyCode);
    }

    public Supplier<Double> rateFor(Currency from, Currency to) {
        return rateFor(from.getCurrencyCode(), to.getCurrencyCode());
    }

    public Supplier<Double> rateFor(String fromCurrencyCode, String toCurrencyCode) {
        return this.rates.computeIfAbsent(fromCurrencyCode, (k) -> new HashMap<>())
                .computeIfAbsent(toCurrencyCode, (k2) -> {
                    if (fromCurrencyCode.equals(toCurrencyCode))
                        return CurrencyExchange::defaultSameRateSupplier;
                    else
                        return CurrencyExchange::defaultRateSupplier;
                });
    }
}
