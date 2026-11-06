package io.didomi.reactnative.test

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
class UICustomRegulationTest : BaseUITest() {

    companion object {
        private const val DELAY = 1_000L
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    // The activity is relaunched for each test, which initializes the SDK with the default notice again
    @Before
    fun init() {
        waitForSdkToBeReady()
        testMethodCall("Initialize custom regulation")
        waitForSdkToBeReady()
    }

    private fun testMethodCall(method: String) {
        tapButton(method)
        waitForDisplayed(withText("$method-OK"))
    }

    @Test
    fun test_getApplicableRegulation() {
        tapButton("getApplicableRegulation")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        assertText("\"custom\"")
    }

    @Test
    fun test_getApplicableRegulationConfiguration() {
        tapButton("getApplicableRegulationConfiguration")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        assertTextContains("\"id\":\"abcd\"")
        assertTextContains("\"versionId\":\"v1\"")
        assertTextContains("\"numericalId\":123")
        assertTextContains("\"regulationName\":\"gdpr\"")
        assertTextContains("\"tokenStorageName\":\"didomi_token_abcd\"")
        assertTextContains("\"mixed\":false")
    }

    @Test
    fun test_getCurrentUserStatus() {
        tapButton("getCurrentUserStatus")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        assertTextContains("\"regulation\":\"custom\"")
        assertTextContains("\"regulation_configuration\":{")
        assertTextContains("\"tokenStorageName\":\"didomi_token_abcd\"")
    }
}
