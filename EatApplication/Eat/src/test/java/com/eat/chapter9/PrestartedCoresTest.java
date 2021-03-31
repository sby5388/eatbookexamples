package com.eat.chapter9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator  on 2019/10/30.
 */
public class PrestartedCoresTest {
    private PrestartedCores mSubjects;


    @Before
    public void setUp() throws Exception {
        mSubjects = new PrestartedCores();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void preloadedQueue() {
        mSubjects.preloadedQueue();
    }
}