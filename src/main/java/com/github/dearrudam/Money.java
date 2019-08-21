package com.github.dearrudam;

public class Money {

    private final String currencyCode;
    private final int amount;

    public Money(String currencyCode, int amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public String currencyCode() {
        return currencyCode;
    }

    public int amount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Money{" +
                "currencyCode='" + currencyCode + '\'' +
                ", amount=" + amount +
                '}';
    }

    public Money times(int multiplier) {
        return new Money(this.currencyCode(), this.amount() * multiplier);
    }

    public Money[] divide(int divisor) {

        if(divisor==0)
            return new Money[]{new Money(this.currencyCode(),0),new Money(this.currencyCode(),0)};

        Money restOfDivision = new Money(this.currencyCode(), this.amount() % divisor);
        Money dividend = new Money(this.currencyCode(), (this.amount() - restOfDivision.amount()) / divisor);
        return new Money[]{dividend,restOfDivision};
    }

    public Money plus(Money another) {
        return new Money(this.currencyCode(),this.amount() + another.amount());
    }

    public Money minus(Money another) {
        return new Money(this.currencyCode(),this.amount() - another.amount());
    }
}
