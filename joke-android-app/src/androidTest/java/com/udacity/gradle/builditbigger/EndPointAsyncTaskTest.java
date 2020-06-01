package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest {

    private IdlingResource mIdlingResource;
    private static final String FAKE_RESPONSE = "Hi, Raul";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class,
            false);

    @Before
    public void setUp() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void openItemFromRecipeListTest() {

        MainActivity mainActivity = mActivityTestRule.getActivity();
        mainActivity.setCallback(new MainActivity.TestActivityCallback() {
            @Override
            public void onResponse(String response) {
                Assert.assertEquals(response, FAKE_RESPONSE);
            }
        });

        onView(withId(R.id.tell_joe_button))
                .perform(click());
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}