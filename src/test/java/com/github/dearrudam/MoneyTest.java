package com.github.dearrudam;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Currency;

import static org.junit.Assert.*;

public class MoneyTest {


    static CurrencyExchange currencyExchange;

    @BeforeClass
    public static void beforeClass() {
        currencyExchange = new CurrencyExchange();
        currencyExchange.addRate("USD", "USD", () -> 1.0);
        currencyExchange.addRate("USD", "CHF", () -> 0.98062);
        currencyExchange.addRate("USD", "BRL", () -> 4.03);
    }

    @Test
    public void testMultiplication() {

        Currency dollar = Currency.getInstance("USD");
        Money ten = new Money(dollar, 10.0);

        Money hundred = ten.times(10);

        assertNotNull(hundred);

        assertEquals(dollar, hundred.currency());
        assertEquals(100.0, hundred.amount().doubleValue(), 0);

    }

    @Test
    public void testDivideAndRemainder() {

        Money ten = new Money("USD", 10);

        Money[] results = ten.divideAndRemainder(2);

        assertNotNull(results);
        assertEquals(2, results.length);

        Money dividend = results[0];
        Money restOfDivision = results[1];

        assertNotNull(dividend);
        assertNotNull(restOfDivision);

        assertEquals("USD", dividend.currency().getCurrencyCode());
        assertEquals(Money.scaledAmountAsBigDecimal(5), dividend.amount());

        assertEquals("USD", restOfDivision.currency().getCurrencyCode());
        assertEquals(Money.scaledAmountAsBigDecimal(0), restOfDivision.amount());

    }

    @Test
    public void testDivide() {

        Money ten = new Money("USD", 10);

        Money dividend = ten.divide(2);

        assertNotNull(dividend);

        assertEquals("USD", dividend.currency().getCurrencyCode());
        assertEquals(Money.scaledAmountAsBigDecimal(5), dividend.amount());


        Money dividend2 = ten.divide(3);

        assertNotNull(dividend2);
        assertEquals("USD", dividend2.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal((10.0 / 3)), dividend2.amount());

    }

    @Test
    public void testDivideByZero() {

        Money ten = new Money("USD", 10);

        Money dividend = ten.divide(0);

        assertNotNull(dividend);

        assertEquals("USD", dividend.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(0), dividend.amount());

        Money zero = new Money("USD", 0);

        Money zeroDividend = ten.divide(0);

        assertNotNull(zeroDividend);

        assertEquals("USD", zeroDividend.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(0), zeroDividend.amount());

    }

    @Test
    public void testDivideAndRemainderByZero() {

        Money ten = new Money("USD", 10);

        Money[] results = ten.divideAndRemainder(0);

        assertNotNull(results);
        assertEquals(2, results.length);

        Money dividend = results[0];
        Money restOfDivision = results[1];

        assertNotNull(dividend);
        assertNotNull(restOfDivision);

        assertEquals("USD", dividend.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(0.0), dividend.amount());

        assertEquals("USD", restOfDivision.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(0.0), restOfDivision.amount());


        results = ten.divideAndRemainder(3);

        assertNotNull(results);
        assertEquals(2, results.length);

        dividend = results[0];
        restOfDivision = results[1];

        assertNotNull(dividend);
        assertNotNull(restOfDivision);

        assertEquals("USD", dividend.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(((10.0 - (10 % 3)) / 3)), dividend.amount());

        assertEquals("USD", restOfDivision.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal((double) 10 % 3), restOfDivision.amount());

    }

    @Test
    public void testPlus() {

        Expression expression = new Money("USD", 10.00).plus(new Money("USD", 10.00));

        assertNotNull(expression);

        Money resultInDollar = expression.reduceTo("USD", currencyExchange);

        assertNotNull(resultInDollar);
        assertEquals("USD", resultInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(20.0), resultInDollar.amount());


        Money resultInFranc = expression.reduceTo("CHF", currencyExchange);

        assertNotNull(resultInFranc);
        assertEquals("CHF", resultInFranc.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(20 * 0.98062), resultInFranc.amount());

        Expression composedExpression =
                new Money("USD", 10.00)
                        .plus(new Money("USD", 10.00))
                        .plus(new Money("USD", 10.00))
                        .plus(new Money("USD", 10.00)
                                .plus(new Money("USD", 10.00)));

        Money resultCompositionInDollar = composedExpression.reduceTo("USD", currencyExchange);

        assertNotNull(resultCompositionInDollar);
        assertEquals("USD", resultCompositionInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(50.0), resultCompositionInDollar.amount());

    }

    @Test
    public void testPlusWithNullableArgs() {

        Expression zeroExpression = new Money("USD", 0.00).plus(null);
        Money zeroInDollar = zeroExpression.reduceTo("USD", currencyExchange);
        assertNotNull(zeroInDollar);
        assertEquals("USD", zeroInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(0.0), zeroInDollar.amount());


        Expression expression = new Money("USD", 10.00).plus(null);
        Money resultInDollar = expression.reduceTo("USD", currencyExchange);
        assertNotNull(resultInDollar);
        assertEquals("USD", resultInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(10.0), resultInDollar.amount());

        Expression composedExpression =
                new Money("USD", 10.00)
                        .plus(null)
                        .plus(new Money("USD", 10.00))
                        .plus(null)
                        .plus(new Money("USD", 10.00));

        Money resultCompositionInDollar = composedExpression.reduceTo("USD", currencyExchange);

        assertNotNull(resultCompositionInDollar);
        assertEquals("USD", resultCompositionInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(30.0), resultCompositionInDollar.amount());

    }

    @Test
    public void testMinus() {

        Money resultInDollar = new Money("USD", 10).times(3)
                .minus(new Money("USD", 10))
                .reduceTo("USD", currencyExchange);

        assertNotNull(resultInDollar);
        assertEquals("USD", resultInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(20.0), resultInDollar.amount());


    }

    @Test
    public void testMinusWithNullableArgs() {

        Expression zeroExpression = new Money("USD", 0.00).minus(null);
        Money zeroInDollar = zeroExpression.reduceTo("USD", currencyExchange);
        assertNotNull(zeroInDollar);
        assertEquals("USD", zeroInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(0.0), zeroInDollar.amount());


        Expression expression = new Money("USD", 10.00).minus(null);
        Money resultInDollar = expression.reduceTo("USD", currencyExchange);
        assertNotNull(resultInDollar);
        assertEquals("USD", resultInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(10.0), resultInDollar.amount());

        Expression composedExpression =
                new Money("USD", 10.00)
                        .minus(null)
                        .minus(new Money("USD", 10.00))
                        .minus(null)
                        .minus(new Money("USD", 10.00));

        Money resultCompositionInDollar = composedExpression.reduceTo("USD", currencyExchange);

        assertNotNull(resultCompositionInDollar);
        assertEquals("USD", resultCompositionInDollar.currency().getCurrencyCode());
        assertEquals(Money.amountAsBigDecimal(-10.0), resultCompositionInDollar.amount());

    }
}
