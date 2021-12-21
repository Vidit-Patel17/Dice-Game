package androidsamples.java.dicegames;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.anyOf;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentedTest {
    @Rule
    public ActivityScenarioRule<WalletActivity> activityRule = new ActivityScenarioRule<>(WalletActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable();
    }

    @Test
    public void dummyTest_findsColorContrastError() {
        onView(withId(R.id.btn_launch_twoormore)).perform(click());
        onView(withId(R.id.txt_balance_label3)).check(matches(withText("Coins")));
    }

    @Test
    public void checkDiceRoll() {
        onView(withId(R.id.btn_die)).perform(click());
        onView(withId(R.id.btn_die)).check(matches(anyOf(withText("1"), withText("2"), withText("3"), withText("4"), withText("5"), withText("6"))));
    }
    @Test
    public void checkBetWithNoCoins() {
        onView(withId(R.id.btn_launch_twoormore)).perform(click());
        try {
            onView(withId(R.id.btn_go)).perform(click());
        }
        catch (IllegalStateException ignored) {}
    }
    @Test
    public void checkInfoButton(){
        onView(withId(R.id.btn_launch_twoormore)).perform(click());
        onView(withId(R.id.btn_info)).perform(click());
        onView(withId(R.id.header)).check(matches(withText(" Rules of the Game ")));
    }

    @Test
    public void checkBackButton(){
        onView(withId(R.id.btn_launch_twoormore)).perform(click());
        onView(withId(R.id.btn_back)).perform(click());
        onView(withId(R.id.txt_info)).check(matches(withText(containsString(("Click")))));
    }
}
