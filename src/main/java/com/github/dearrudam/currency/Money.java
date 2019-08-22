package com.github.dearrudam.currency;

import java.util.Objects;

public class Money {

    private final String currencyCode;
    private final long amount;

    public Money(String currencyCode, long amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount &&
                currencyCode.equals(money.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode, amount);
    }
}
