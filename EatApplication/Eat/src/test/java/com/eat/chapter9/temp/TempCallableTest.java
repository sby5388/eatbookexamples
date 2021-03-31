package com.eat.chapter9.temp;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator  on 2019/11/25.
 */
public class TempCallableTest {
    private TempCallable mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = new TempCallable();
    }

    @Test
    public void future() {
        mSubject.myFuture();
    }
}