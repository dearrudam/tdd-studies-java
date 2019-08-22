package com.github.dearrudam.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    void testEquality(){
        Assertions.assertEquals(new Money("USD",10),new Money("USD",10));
    }
}
