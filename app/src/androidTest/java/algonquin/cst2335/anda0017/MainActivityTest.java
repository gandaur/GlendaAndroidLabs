package algonquin.cst2335.anda0017;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * testing if the textview is changing to "You shall not pass!"
     */
    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(withId(R.id.password_field));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.login_button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.password_message));
        textView.check(matches(withText("You shall not pass")));


    }

    /**
     * testing if the textview is changing to "Your password is complex enough"
     */
    @Test
    public void mainActivityTest1() {


        ViewInteraction appCompatEditText = onView(withId(R.id.password_field));
        appCompatEditText.perform(replaceText("passWORD123#$*"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.login_button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.password_message));
        textView.check(matches(withText("Your password is complex enough")));


    }

    /**
     * Tests password with uppercase missing
     */
    @Test
    public void mainActivityTest2() {


        ViewInteraction appCompatEditText = onView(withId(R.id.password_field));
        appCompatEditText.perform(replaceText("password123#$*"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.login_button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.password_message));
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * Tests password with lowercase missing
     */
    @Test
    public void mainActivityTest3() {


        //find the view s
        ViewInteraction appCompatEditText = onView(withId(R.id.password_field));
        //type in password123#$*
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.login_button));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.password_message));
        //check the text
        textView.check(matches(withText("You shall not pass!")));

    }

    /**
     * testing if the app can recognise the absence of number
     */
    @Test
    public void mainActivityTest4() {
        //find the view s
        ViewInteraction appCompatEditText = onView(withId(R.id.password_field));
        //type in password123#$*
        appCompatEditText.perform(replaceText("passWORD#%$"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.login_button));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.password_message));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * testing if the app can recognise the absence of special character
     */
    @Test
    public void mainActivityTest5() {
        //find the view s
        ViewInteraction appCompatEditText = onView(withId(R.id.password_field));
        //type in password123#$*
        appCompatEditText.perform(replaceText("passWORD123"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.login_button));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.password_message));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
}

