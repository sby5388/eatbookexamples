package com.eat.chapter9;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator  on 2019/11/25.
 */
public class TempTest {
    private Temp mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = new Temp();
    }

    @Test
    public void testTemp() {
        mSubject.temp();
    }
}