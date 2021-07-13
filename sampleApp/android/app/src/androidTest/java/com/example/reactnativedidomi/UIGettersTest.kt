package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
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
class UIGettersTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        // EVENT ON_READY NOT SENT ON SUCCESSIVE TESTS,
        // HAVE TO WAIT TO BE SURE THAT THE SDK IS READY
        Thread.sleep(5000L)

        // Make sure view is ready before starting test
        waitForDisplayed(withText("RESET"))
    }

    @After
    fun tearDown() {
    }

    private fun testMethodCall(method: String, needToScroll: Boolean) {
        onView(withText(method.toUpperCase())).perform(scrollTo(), click())

        if (needToScroll)
            onView(withText(method.toUpperCase())).perform(ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp())
        waitForDisplayed(withText("$method-OK"))
    }

    @Test
    fun test_GetDisabledPurposes() {
        testMethodCall("getDisabledPurposes", true)
    }

    @Test
    fun test_GetDisabledPurposeIds() {
        testMethodCall("getDisabledPurposeIds", true)
    }

    @Test
    fun test_GetDisabledVendors() {
        testMethodCall("getDisabledVendors", true)
    }

    @Test
    fun test_GetDisabledVendorIds() {
        testMethodCall("getDisabledVendorIds", true)
    }

    @Test
    fun test_GetEnabledPurposes() {
        testMethodCall("getEnabledPurposes", true)
    }

    @Test
    fun test_GetEnabledPurposeIds() {
        testMethodCall("getEnabledPurposeIds", true)
    }

    @Test
    fun test_GetEnabledVendors() {
        testMethodCall("getEnabledVendors", true)
    }

    @Test
    fun test_GetEnabledVendorIds() {
        testMethodCall("getEnabledVendorIds", true)
    }
}