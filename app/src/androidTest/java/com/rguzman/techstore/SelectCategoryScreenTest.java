package com.rguzman.techstore;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.rguzman.techstore.presentation.category.CategoryListActivity;
import com.rguzman.techstore.presentation.category.CategoryListFragment;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SelectCategoryScreenTest {
    @Rule
    public ActivityTestRule<CategoryListActivity> mActivityTestRule = new ActivityTestRule<>(CategoryListActivity.class,
            false);

    public static final int DESIRED_INDEX_FOR_TESTING = 0;

    private IdlingResource mIdlingResource;
    private String detailTitle = "";

    @Before
    public void registerIdlingResource() {
        CategoryListFragment recipeListFragment = (CategoryListFragment) mActivityTestRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_category_list);
        mIdlingResource = recipeListFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void openItemFromRecipeListTest() {

        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(DESIRED_INDEX_FOR_TESTING, new ViewAction() {

                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return "Click on a child view with specified id.";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        detailTitle = ((TextView) view.findViewById(R.id.txt_category_title)).getText().toString();
                        view.performClick();
                    }
                }));

        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class)))).check(matches(withText(detailTitle)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
