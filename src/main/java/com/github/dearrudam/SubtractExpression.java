package com.github.dearrudam;

import java.util.Optional;

public class SubtractExpression implements Expression {

    private final Expression leftPair;
    private final Expression rightPair;

    public SubtractExpression(Expression leftPair, Expression rightPair) {
        this.leftPair = leftPair;
        this.rightPair = rightPair;
    }

    @Override
    public Money reduceTo(String currencyCode, CurrencyExchange currencyExchange) {
        Expression zeroMoney = new Money(currencyCode, 0);
        Money leftResult = Optional.ofNullable(this.leftPair).orElse(zeroMoney).reduceTo(currencyCode, currencyExchange);
        Money rightResult = Optional.ofNullable(this.rightPair).orElse(zeroMoney).reduceTo(currencyCode, currencyExchange);
        return new Money(currencyCode, leftResult.amount().doubleValue() - rightResult.amount().doubleValue());
    }
}
