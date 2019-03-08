package com.udacity.gradle.builditbigger;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class EndPointTest {

    public static final String FAKE_NAME = "James";
    private static final String FAKE_RESPONSE = "Hi, " + FAKE_NAME;
    public EndPointAsyncTask asyncTask = null;

    @Before
    public void setUpCounter() {
        this.asyncTask = new EndPointAsyncTask();
    }

    @Test
    public void testIncrement() throws InterruptedException {
        final Object syncObject = new Object();
        asyncTask.setCallback(new EndPointAsyncTask.EndPointCallback() {
            @Override
            public void onResponse(String response) {
                Assert.assertEquals(response, FAKE_RESPONSE);
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        });


        asyncTask.execute(FAKE_NAME);
        synchronized (syncObject) {
            syncObject.wait();
        }
    }
}
