package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
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
class UISettersTest {

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
        onView(withText(method.toUpperCase())).perform(scrollTo(), click())
        waitForDisplayed(withText("$method-OK"))
    }

    @Test
    fun test_SetUserStatusSets() {
        testMethodCall("setUserStatusSets")
    }

    @Test
    fun test_SetUserAgreeToAll() {
        testMethodCall("setUserAgreeToAll")
    }

    @Test
    fun test_SetUserDisagreeToAll() {
        testMethodCall("setUserDisagreeToAll")
    }
}