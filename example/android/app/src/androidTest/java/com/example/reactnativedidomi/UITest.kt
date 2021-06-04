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

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UITest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {

    }

    private fun testMethodCall(method: String) {
        EspressoViewFinder.waitForDisplayed(withText(method.toUpperCase()))
        onView(withText(method.toUpperCase())).perform(click())
        EspressoViewFinder.waitForDisplayed(withText("$method-OK"))
    }

    private fun testLastEvent(event: String) {

        EspressoViewFinder.waitForDisplayed(withText("LAST RECEIVED EVENT: $event"))
    }

    @Test
    fun test_SDKInitOK() {
        testLastEvent("on_ready")
    }

    @Test
    fun test_Reset() {
        testMethodCall("reset")
    }

    fun test_SetupUI() {
        testMethodCall("setupUI")

        // Check opening of notice
        val agreeButton = onView(withText("Agree & Close"))
        agreeButton.check(matches(isDisplayed()))

        testLastEvent("on_show_notice")

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_notice_click_agree")
        testLastEvent("on_hide_notice")
    }

    @Test
    fun test_SetLogLevel() {
        testMethodCall("setLogLevel")
    }

}