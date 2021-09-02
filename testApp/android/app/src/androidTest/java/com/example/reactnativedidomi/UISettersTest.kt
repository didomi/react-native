package com.example.reactnativedidomi

import android.util.Log
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
import io.didomi.sdk.exceptions.DidomiNotReadyException
import org.junit.After

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UISettersTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
    }

    private fun testMethodCall(method: String, needToScroll: Boolean) {
        Log.d("Didomi Test", "Testing $method")
        try {
            onView(withText(method.toUpperCase())).perform(scrollTo(), click())

            if (needToScroll)
                onView(withText(method.toUpperCase())).perform(ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp(), ViewActions.swipeUp())
            waitForDisplayed(withText("$method-OK"))
        } catch (e: DidomiNotReadyException) {
            e.printStackTrace()
        }
    }

    @Test
    fun test_SetUserStatusSets() {
        testMethodCall("setUserStatusSets", true)
    }

    @Test
    fun test_SetUserAgreeToAll() {
        testMethodCall("setUserAgreeToAll", true)
    }

    @Test
    fun test_SetUserDisagreeToAll() {
        testMethodCall("setUserDisagreeToAll", true)
    }
}