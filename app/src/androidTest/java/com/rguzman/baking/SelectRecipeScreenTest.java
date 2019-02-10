package com.rguzman.baking;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rguzman.baking.presentation.recipe.RecipeListActivity;
import com.rguzman.baking.presentation.recipe.RecipeListFragment;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class SelectRecipeScreenTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class,
            false);

    public static final int DESIRED_INDEX_FOR_TESTING = 0;

    private IdlingResource mIdlingResource;
    private String detailTitle = "";

    @Before
    public void registerIdlingResource() {
        RecipeListFragment recipeListFragment = (RecipeListFragment) mActivityTestRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.master_list_fragment);
        mIdlingResource = recipeListFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void openItemFromRecipeListTest() {

        onView(withId(R.id.recyclerRecipe)).perform(
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
                        detailTitle = ((TextView) view.findViewById(R.id.txtRecipeName)).getText().toString();
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
