package com.github.dearrudam;

public interface Expression {

    Money reduceTo(String currencyCode, CurrencyExchange currencyExchange);

    default Expression plus(Expression amendment) {
        return new SumExpression(this, amendment);
    }

    default Expression minus(Expression amendment) {
        return new SubtractExpression(this,amendment);
    }
}
