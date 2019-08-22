package com.github.dearrudam;

import java.util.Optional;
import java.util.function.Supplier;

public class SumExpression implements Expression {

    private final Expression leftPair;
    private final Expression rightPair;

    public SumExpression(Expression leftPair, Expression rightPair) {
        this.leftPair = leftPair;
        this.rightPair = rightPair;
    }

    @Override
    public Money reduceTo(String currencyCode, CurrencyExchange currencyExchange) {

        Expression zeroMoney = new Money(currencyCode,0);
        Money leftResult= Optional.ofNullable(this.leftPair).orElse(zeroMoney).reduceTo(currencyCode,currencyExchange);
        Money rightResult=Optional.ofNullable(this.rightPair).orElse(zeroMoney).reduceTo(currencyCode,currencyExchange);

        Money total;
        if (leftResult.currency().equals(rightResult.currency())) {
            total = new Money(leftResult.currency(), leftResult.amount().doubleValue() + rightResult.amount().doubleValue());
            Supplier<Double> leftRate = currencyExchange.rateFor(leftResult.currency(), currencyCode);
            return new Money(currencyCode, total.amount().doubleValue() * leftRate.get());
        }

        Supplier<Double> leftRate = currencyExchange.rateFor(leftResult.currency(), currencyCode);
        Money leftAmount = new Money(currencyCode, leftResult.amount().doubleValue() * leftRate.get());

        Supplier<Double> rightRate = currencyExchange.rateFor(rightResult.currency(), currencyCode);
        Money rightAmount = new Money(currencyCode, rightResult.amount().doubleValue() * rightRate.get());

        return new Money(currencyCode, leftAmount.amount().doubleValue() + rightAmount.amount().doubleValue());
    }
}
