package com.example.reactnativedidomi

import androidx.test.espresso.Espresso
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
import org.junit.After

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIMethodsTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        //TODO EVENT ON_READY NOT SENT ON SUCCESSIVE TESTS
        Thread.sleep(5000L)

        //testLastEvent("on_ready")
        testMethodCall("reset")
    }

    @After
    fun tearDown() {
        testMethodCall("reset")
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
        methodCall("setupUI")

        // Check opening of notice
        val agreeButtonText = "Agree & Close"

        Thread.sleep(3000L)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_notice_click_agree")
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
        methodCall("showNotice")

        val agreeButtonText = "Agree & Close"

        Thread.sleep(3000L)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_notice_click_agree")
    }

    @Test
    fun test_ShowPreferencesPurposes() {
        methodCall("showPreferences Purposes")

        val agreeButtonText = "Save"

        Thread.sleep(3000L)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")
    }

    @Test
    fun test_ShowPreferencesVendors() {
        methodCall("showPreferences Vendors")

        var agreeButtonText = "Save"

        Thread.sleep(3000L)
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