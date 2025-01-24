package io.didomi.reactnative.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.didomi.reactnative.test.EspressoViewFinder.waitForDisplayed
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIInitializeTest : BaseUITest() {

    companion object {
        private const val DELAY = 1_000L
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() = waitForSdkToBeReady()

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
        Thread.sleep(DELAY)
        assertText("\"gdpr\"")
    }

    @Test
    fun test_NoticeCalifornia() {
        testMethodCall("Initialize US CA")
        waitForSdkToBeReady()

        tapButton("GetUserStatus Regulation")
        Thread.sleep(DELAY)
        assertText("\"cpra\"")
    }

    @Test
    fun test_InitializeUnderage() {
        testMethodCall("Initialize underage notice")
        waitForSdkToBeReady()

        tapButton("Setup UI")
        Thread.sleep(DELAY)
        assertText("\"underage notice text\"")
    }

    @Test
    fun test_InitializeWithParameters() {
        testMethodCall("Initialize with parameters")
        waitForSdkToBeReady()

        tapButton("GetUserStatus Regulation")
        Thread.sleep(DELAY)
        assertText("\"gdpr\"")
    }
}
