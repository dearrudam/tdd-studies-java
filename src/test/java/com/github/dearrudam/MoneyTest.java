package com.github.dearrudam;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {

    @Test
    public void testMultiplication() {

        Money ten = new Money("USD", 10);

        Money hundred = ten.times(10);

        assertNotNull(hundred);

        assertEquals("USD", hundred.currencyCode());
        assertEquals(100, hundred.amount());

    }

    @Test
    public void testDivision() {

        Money ten = new Money("USD", 10);

        Money[] results = ten.divide(2);

        assertNotNull(results);
        assertEquals(2, results.length);

        Money dividend = results[0];
        Money restOfDivision = results[1];

        assertNotNull(dividend);
        assertNotNull(restOfDivision);

        assertEquals("USD", dividend.currencyCode());
        assertEquals(5, dividend.amount());

        assertEquals("USD", restOfDivision.currencyCode());
        assertEquals(0, restOfDivision.amount());

    }

    @Test
    public void testDivisionByZero() {

        Money ten = new Money("USD", 10);

        Money[] results = ten.divide(0);

        assertNotNull(results);
        assertEquals(2, results.length);

        Money dividend = results[0];
        Money restOfDivision = results[1];

        assertNotNull(dividend);
        assertNotNull(restOfDivision);

        assertEquals("USD", dividend.currencyCode());
        assertEquals(0, dividend.amount());

        assertEquals("USD", restOfDivision.currencyCode());
        assertEquals(0, restOfDivision.amount());

    }

    @Test
    public void testPlus() {

        Money twenty = new Money("USD", 10).plus(new Money("USD", 10));

        assertNotNull(twenty);

        assertEquals("USD",twenty.currencyCode());
        assertEquals(20,twenty.amount());

    }

    @Test
    public void testMinus() {

        Money zero = new Money("USD", 10).minus(new Money("USD", 10));

        assertNotNull(zero);

        assertEquals("USD",zero.currencyCode());
        assertEquals(0,zero.amount());

    }
}
