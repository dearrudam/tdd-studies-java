package com.github.dearrudam.currency;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyRateRegistry {

    private final Map<String, Map<String, Double>> rates = new ConcurrentHashMap<>();

    public void addRate(String fromCurrencyCode, String toCurrencyCode, double newRate) {
        this.rates.computeIfAbsent(fromCurrencyCode, (key) -> new ConcurrentHashMap<>()).compute(toCurrencyCode, (key, rate) -> newRate);
        this.rates.computeIfAbsent(toCurrencyCode, (key) -> new ConcurrentHashMap<>()).compute(fromCurrencyCode, (key, rate) -> newRate * (-1));
    }

    public double rate(String fromCurrencyCode, String toCurrencyCode) {
        if (fromCurrencyCode.equals(toCurrencyCode))
            return 1.0;
        return this.rates.get(fromCurrencyCode).get(toCurrencyCode);
    }
}
