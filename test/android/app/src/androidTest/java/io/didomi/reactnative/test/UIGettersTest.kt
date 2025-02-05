package io.didomi.reactnative.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersTest : BaseUITest() {

    companion object {
        private const val DELAY = 1_000L
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() = waitForSdkToBeReady()

    @Test
    fun test_GetJavaScriptForWebView() {
        tapButton("getJavaScriptForWebView")

        // Asserting the whole string can be tricky so we just assert the beginning of it.
        val expected = "\"window.didomiOnReady = window.didomiOnReady || [];window.didomiOnReady.push(function (Didomi) {".trim()

        // There might be a delay to get this string.
        Thread.sleep(DELAY)
        assertTextStartsWith(expected)
    }

    @Test
    fun test_GetQueryStringForWebView() {
        tapButton("getQueryStringForWebView")

        // Asserting the whole string can be tricky so we just assert the beginning of it.
        val expected = "\"didomiConfig.user.externalConsent.value".trim()

        // There might be a delay to get this string.
        Thread.sleep(DELAY)
        assertTextStartsWith(expected)
    }

    @Test
    fun test_isUserStatusPartial() {
        tapButton("reset")

        tapButton("isUserStatusPartial")

        assertText("true")

        disagreeToAll()

        tapButton("isUserStatusPartial")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)
        assertText("false")
    }

    @Test
    fun test_shouldUserStatusBeCollected() {
        tapButton("reset")

        tapButton("shouldUserStatusBeCollected")

        assertText("true")

        disagreeToAll()

        tapButton("shouldUserStatusBeCollected")

        assertText("false")
    }

    /** getUserStatus */

    @Test
    fun test_getUserStatus() {
        tapButton("getUserStatus")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        // The text might change every time we call the getUserStatus method
        // so we'll only assert the first level parameters of the resulting json string.
        assertTextContains("\"addtl_consent\":\"\"".trim())
        assertTextContains("\"consent_string\":\"".trim())
        assertTextContains("\"purposes\":{\"".trim())
        assertTextContains("\"vendors\":{\"".trim())
        assertTextContains("\"user_id\":\"".trim())
        assertTextContains("\"created\":\"".trim())
        assertTextContains("\"updated\":\"".trim())
        assertTextContains("\"didomi_dcs\":\"\"".trim()) // DCS feature flag is disabled (empty string)
        assertTextContains("\"regulation\":\"gdpr\"".trim())
    }

    @Test
    fun test_getUserStatus_purposes() {
        tapButton("getUserStatus purposes")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        // The text might change every time we call the getUserStatus method
        // so we'll only assert the first level parameters of the resulting json string.
        assertTextContains("\"legitimate_interest\":{\"enabled\":[".trim())
        assertTextContains("\"global\":{\"enabled\":[".trim())
        assertTextContains("\"essential\":[".trim())
        assertTextContains("\"consent\":{\"enabled\":[".trim())
    }

    @Test
    fun test_getUserStatus_vendors() {
        tapButton("getUserStatus vendors")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        // The text might change every time we call the getUserStatus method
        // so we'll only assert the first level parameters of the resulting json string.
        assertTextContains("\"legitimate_interest\":{\"enabled\":[".trim())
        assertTextContains("\"global\":{\"enabled\":[".trim())
        assertTextContains("\"global_li\":{\"enabled\":[".trim())
        assertTextContains("\"global_consent\":{\"enabled\":[".trim())
        assertTextContains("\"consent\":{\"enabled\":[".trim())
    }

    @Test
    fun test_getUserStatus_vendors_global_consent() {
        tapButton("getUserStatus vendors global_consent")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        // The text might change every time we call the getUserStatus method
        // so we'll only assert the first level parameters of the resulting json string.
        assertTextContains("{\"enabled\":[".trim())
        assertTextContains(",\"disabled\":[".trim())
        assertTextContains("]}".trim())
    }

    /** getCurrentUserStatus */

    @Test
    fun test_getCurrentUserStatus() {
        tapButton("getCurrentUserStatus")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        // The text might change every time we call the getCurrentUserStatus method
        // so we'll only assert the first level parameters of the resulting json string.
        assertTextContains("\"addtl_consent\":\"\"".trim())
        assertTextContains("\"consent_string\":\"".trim())
        assertTextContains("\"purposes\":{\"".trim())
        assertTextContains("\"vendors\":{\"".trim())
        assertTextContains("\"user_id\":\"".trim())
        assertTextContains("\"created\":\"".trim())
        assertTextContains("\"updated\":\"".trim())
        assertTextContains("\"didomi_dcs\":\"\"".trim()) // DCS feature flag is disabled (empty string)
        assertTextContains("\"regulation\":\"gdpr\"".trim())
    }

    /** Applicable regulation */

    @Test
    fun test_getApplicableRegulation() {
        tapButton("getApplicableRegulation")

        // There might be a delay to get this string.
        Thread.sleep(DELAY)

        assertText("\"gdpr\"")
    }

    /** Vendor count */

    @Test
    fun test_getVendorCount() {
        tapButton("Get vendor count")

        assertText("\"Total: 3 - IAB: 3 - Non-IAB: 0\"")
    }
}
