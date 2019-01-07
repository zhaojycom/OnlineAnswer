package com.zhaojy.onlineanswer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zhaojy.onlineanswer.mvp.view.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void test() {
        onView(withId(R.id.phoneInput)).perform(typeText("18774512067"),
                closeSoftKeyboard());
        onView(withId(R.id.loginBt)).perform(click());
    }

}
