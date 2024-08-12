package io.didomi.reactnative.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
class UIMethodsTest : BaseUITest() {

    companion object {
        private const val DELAY_LONG = 2_000L
        private const val DELAY_SHORT = 1_000L
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
        testMethodCall("reset")
    }

    private fun testMethodCall(method: String) {
        waitForDisplayed(withText(method.uppercase()))
        onView(withText(method.uppercase())).perform(click())
        waitForDisplayed(withText("$method-OK"))
    }

    private fun methodCall(method: String) {
        waitForDisplayed(withText(method.uppercase()))
        onView(withText(method.uppercase())).perform(click())
    }

    private fun testLastEvent(event: String) {
        waitForDisplayed(withText("LAST RECEIVED EVENT: $event"))
    }

    fun test_SDKInitOK() {
        testLastEvent("on_ready")
    }

    @Test
    fun test_SetupUI() {
        testMethodCall("reset")
        methodCall("setupUI")

        // Check opening of notice
        val agreeButtonText = "Agree & Close"

        Thread.sleep(DELAY_SHORT)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")
        waitForDisplayed(withText("setupUI-OK"))

        // Let some time after Didomi UI was closed
        Thread.sleep(DELAY_SHORT)
    }

    @Test
    fun test_SetLogLevel() {
        testMethodCall("setLogLevel")

        waitForDisplayed(withText("setLogLevel-OK"))
    }

    @Test
    fun test_UpdateSelectedLanguage() {
        testMethodCall("updateSelectedLanguage")

        waitForDisplayed(withText("updateSelectedLanguage-OK"))
        testLastEvent("on_language_updated\"fr\"")
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

        Thread.sleep(DELAY_SHORT)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")

        // Let some time after Didomi UI was closed
        Thread.sleep(DELAY_SHORT)
    }

    @Test
    fun test_ShowPreferencesPurposes() {
        testMethodCall("reset")
        methodCall("showPreferences Purposes")

        val agreeButtonText = "Agree to all"

        Thread.sleep(DELAY_SHORT)
        waitForDisplayed(withText(agreeButtonText))
        val agreeButton = onView(withText(agreeButtonText))
        agreeButton.check(matches(isDisplayed()))

        // Close notice
        agreeButton.perform(click())

        testLastEvent("on_hide_notice")

        // Let some time after Didomi UI was closed
        Thread.sleep(DELAY_SHORT)
    }

    @Test
    fun test_ShowPreferencesVendors() {
        testMethodCall("reset")
        methodCall("showPreferences Vendors")

        var agreeButtonText = "Save"

        Thread.sleep(DELAY_SHORT)
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

        // Let some time after Didomi UI was closed
        Thread.sleep(DELAY_SHORT)
    }

    @Test
    fun test_VendorStatusListener() {
        testMethodCall("reset")

        methodCall("Listen ipromote Vendor status")
        waitForDisplayed(withText("Listen ipromote Vendor status-OK"))

        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY_LONG)
        assertText("setUserAgreeToAll-OK")

        testLastEvent("Vendor status ipromote -> true")

        tapButton("Restore event listeners")
        waitForDisplayed(withText("Restore event listeners-OK"))

        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY_LONG)
        assertText("setUserDisagreeToAll-OK")

        testLastEvent("on_consent_changed")
    }
}
