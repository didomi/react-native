package io.didomi.reactnative.test

import android.view.View
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.didomi.reactnative.test.EspressoViewFinder.waitForDisplayed
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIWidgetsTest : BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    // The activity is relaunched for each test, which closes the widget and initializes the SDK with the default notice again
    @Before
    fun init() {
        waitForSdkToBeReady()
        testMethodCall("Initialize widget notice")
        waitForSdkToBeReady()
    }

    private fun testMethodCall(method: String) {
        tapButton(method)
        waitForDisplayed(withText("$method-OK"))
    }

    @Test
    fun test_ShowWidget() {
        tapButton("showWidget")

        // The widget is displayed in a dialog covering the app: look for the events in the app window.
        // The test app hides the widget 2 seconds after it is displayed, logging its visibility first.
        val appWindow = withDecorView(`is`(appDecorView()))
        waitForDisplayed(
            withText(allOf(containsString("> on_show_widget"), containsString("\"widgetId\":\"widget_cpra\""))),
            rootMatcher = appWindow
        )
        waitForDisplayed(withText(containsString("> isWidgetVisible widget_cpra -> true")), rootMatcher = appWindow)
        waitForDisplayed(withText(containsString("> on_hide_widget")), rootMatcher = appWindow)

        tapButton("isWidgetVisible")
        assertText("false")
    }

    private fun appDecorView(): View {
        lateinit var decorView: View
        activityRule.scenario.onActivity { decorView = it.window.decorView }
        return decorView
    }
}
