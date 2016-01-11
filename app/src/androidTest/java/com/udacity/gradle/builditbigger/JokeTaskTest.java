package com.udacity.gradle.builditbigger;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import com.example.JokeProvider;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class JokeTaskTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    private static final String TAG = "JokeTaskTest";
    final CountDownLatch signal = new CountDownLatch(1);
    boolean called = false;
    public JokeTaskTest()
    {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
    }

    @MediumTest
    public void testAsyncTask() throws Throwable
    {
        final CountDownLatch signal = new CountDownLatch(1);
        runTestOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                TestFriendlyAsyncTask task = new TestFriendlyAsyncTask();
                task.execute();
                called = true;
            }
        });
        signal.await(30, TimeUnit.SECONDS);
        assertTrue(called);
        // To wait for the AsyncTask to complete, you can safely call get() from the test thread
    }

    private class TestFriendlyAsyncTask extends JokeExtractingAsyncTask
    {
        @Override
        protected void onPostExecute(String result)
        {
            assertTrue(result != null);
            Log.e(TAG, "onPostExecute: result => " + result);
            assertTrue(Arrays.asList(JokeProvider.getAllJokes()).contains(result));
            signal.countDown();
        }
    }
}