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
class UIInitializeTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
    }

    private fun testMethodCall(method: String) {
        waitForDisplayed(withText(method.uppercase()))
        onView(withText(method.uppercase())).perform(click())
        waitForDisplayed(withText("$method-OK"))
    }

    @Test
    fun test_NoticeFR() {
        testMethodCall("Initialize FR")
        waitForSdkToBeReady()

        tapButton("GetUserStatus Regulation")
        Thread.sleep(1_000L)
        assertText("\"gdpr\"")
    }

    @Test
    fun test_NoticeCalifornia() {
        testMethodCall("Initialize US CA")
        waitForSdkToBeReady()

        tapButton("GetUserStatus Regulation")
        Thread.sleep(1_000L)
        assertText("\"cpra\"")
    }
}
