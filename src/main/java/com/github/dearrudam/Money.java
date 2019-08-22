package com.github.dearrudam;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.function.Supplier;

public class Money implements Expression {

    private final Currency currency;
    private final long amount;
    private static final int[] cents = new int[]{1, 10, 100, 1000, 10000, 100000};

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = Math.round(amount * centsFactor());
    }

    public Money(Currency currency, long amount) {
        this.currency = currency;
        this.amount = amount * centsFactor();
    }

    private int centsFactor() {
        return cents[cents.length - 1];
    }

    public Money(String currencyCode, double amount) {
        this(Currency.getInstance(currencyCode), amount);
    }

    public Currency currency() {
        return this.currency;
    }

    public BigDecimal amount() {
        return unscaledAmountAsBigDecimal(this.amount);
    }

    public static BigDecimal unscaledAmountAsBigDecimal(long amount) {
        return BigDecimal.valueOf(amount, 5);
    }

    public static BigDecimal scaledAmountAsBigDecimal(long amount) {
        return unscaledAmountAsBigDecimal(amount * cents[5]);
    }

    public static BigDecimal amountAsBigDecimal( double amount) {
        return unscaledAmountAsBigDecimal(Math.round(amount * cents[5]));
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }

    public Money times(double multiplier) {
        return new Money(this.currency(),
                this.amount().multiply(BigDecimal.valueOf(multiplier))
                        .doubleValue());
    }

    public Money[] divideAndRemainder(double divisor) {
        if (divisor == 0)
            return new Money[]{new Money(this.currency(), 0), new Money(this.currency(), 0)};

        BigDecimal[] result = this.amount().divideAndRemainder(BigDecimal.valueOf(divisor));
        Money dividend = new Money(this.currency(), result[0].doubleValue());
        Money restOfDivision = new Money(this.currency(), result[1].doubleValue());
        return new Money[]{dividend, restOfDivision};
    }

    @Override
    public Money reduceTo(String currencyCode, CurrencyExchange currencyExchange) {
        Supplier<Double> leftRate = currencyExchange.rateFor(this.currency(), currencyCode);
        return new Money(currencyCode, this.amount().doubleValue() * leftRate.get());
    }

    public Money divide(int divisor) {
        if (divisor == 0)
            return new Money(this.currency(), 0);
        return new Money(this.currency(),this.amount().doubleValue() / divisor);
    }
}
