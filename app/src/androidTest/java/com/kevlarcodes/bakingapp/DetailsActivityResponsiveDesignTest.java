package com.kevlarcodes.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kevlarcodes.bakingapp.activities.DetailsActivity;
import com.kevlarcodes.bakingapp.models.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityResponsiveDesignTest {

    private static final int TABLET_SIZE = 600;

    @Rule
    public final ActivityTestRule<DetailsActivity> mActivityTestRule =
            new ActivityTestRule<>(DetailsActivity.class, false, false);


    @Test
    public void twoFrameViewTest() {


        mActivityTestRule.launchActivity(loadIntent());

        int size = mActivityTestRule.getActivity()
                .getApplicationContext()
                .getResources()
                .getConfiguration()
                .screenWidthDp;


        if (size < TABLET_SIZE) {
            onView(withId(R.id.steps_container))
                    .check(matches(isDisplayed()));

        } else {
            onView(withId(R.id.steps_container))
                    .check(matches(isDisplayed()));


            onView(withId(R.id.step_detail_container))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void verifyIngredientsTest() {

        mActivityTestRule.launchActivity(loadIntent());

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_ingred), withText("Apples")));
        textView.check(matches(withText("Apples")));
    }

    private static Intent loadIntent() {

        Recipe recipe;
        TestCases testData = new TestCases();
        recipe = testData.testDataLoad();

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, DetailsActivity.class);


        intent.putExtra("thisRecipe", recipe);
        return intent;
    }

}
