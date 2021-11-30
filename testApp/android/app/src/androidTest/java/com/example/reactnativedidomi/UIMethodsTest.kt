package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIMethodsTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
    }

    private fun testMethodCall(method: String) {
        waitForDisplayed(withText(method.toUpperCase()))
        onView(withText(method.toUpperCase())).perform(click())
        waitForDisplayed(withText("$method-OK"))
    }

    private fun methodCall(method: String) {
        waitForDisplayed(withText(method.toUpperCase()))
        onView(withText(method.toUpperCase())).perform(click())
    }

    private fun testLastEvent(event: String) {
        waitForDisplayed(withText("LAST RECEIVED EVENT: $event"))
    }

    fun test_SDKInitOK() {
        testLastEvent("on_ready")
    }

    @Test
    fun test_Reset() {
        testMethodCall("reset")
    }

    @Test
    fun test_SetupUI() {
        testMethodCall("reset")
        methodCall("setupUI")

        // Check opening of notice
        val agreeButtonText = "Agree & Close"

        Thread.sleep(1000L)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")
        waitForDisplayed(withText("setupUI-OK"))

    }

    @Test
    fun test_SetLogLevel() {
        testMethodCall("setLogLevel")
    }

    @Test
    fun test_HideNotice() {
        testMethodCall("hideNotice")
        testLastEvent("on_hide_notice")
    }

    @Test
    fun test_HidePreferences() {
        testMethodCall("hidePreferences")
    }

    @Test
    fun test_ShowNotice() {
        testMethodCall("reset")
        methodCall("showNotice")

        val agreeButtonText = "Agree & Close"


        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")
    }

    @Test
    fun test_ShowPreferencesPurposes() {
        testMethodCall("reset")
        methodCall("showPreferences Purposes")

        val agreeButtonText = "Agree to all"


        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")
    }

    @Test
    fun test_ShowPreferencesVendors() {
        testMethodCall("reset")
        methodCall("showPreferences Vendors")

        var agreeButtonText = "Save"


        waitForDisplayed(withText(agreeButtonText))
        var agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        // Close the purpose notice
        agreeButtonText = "Agree to all"
        waitForDisplayed(withText(agreeButtonText))
        agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")
    }

}
