package com.example.reactnativedidomi

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
    }

    @Test
    fun test_GetDisabledPurposes() {
        disagreeToAll()

        tapButton("getDisabledPurposes")
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetDisabledPurposeIds() {
        disagreeToAll()

        tapButton("getDisabledPurposeIds")
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetDisabledVendors() {
        disagreeToAll()

        tapButton("getDisabledVendors")
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetDisabledVendorIds() {
        disagreeToAll()

        tapButton("getDisabledVendorIds")
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetEnabledPurposes() {
        agreeToAll()

        tapButton("getEnabledPurposes")
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetEnabledPurposeIds() {
        agreeToAll()

        tapButton("getEnabledPurposeIds")
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetEnabledVendors() {
        agreeToAll()

        tapButton("getEnabledVendors")
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetEnabledVendorIds() {
        agreeToAll()

        tapButton("getEnabledVendorIds")
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetJavaScriptForWebView() {
        tapButton("getJavaScriptForWebView")

        // Asserting the whole string can be tricky so we just assert the beginning of it.
        val expected = "\"window.didomiOnReady = window.didomiOnReady || [];window.didomiOnReady.push(function (Didomi) {".trim()

        // There might be a delay to get this string.
        Thread.sleep(1_000L)
        assertTextStartsWith(expected)
    }

    @Test
    fun test_GetQueryStringForWebView() {
        tapButton("getQueryStringForWebView")

        // Asserting the whole string can be tricky so we just assert the beginning of it.
        val expected = "\"didomiConfig.user.externalConsent.value".trim()

        // There might be a delay to get this string.
        Thread.sleep(1_000L)
        assertTextStartsWith(expected)
    }

    @Test
    fun test_getUserStatus() {
        tapButton("getUserStatus")

        // There might be a delay to get this string.
        Thread.sleep(1_000L)

        // The text might change every time we call the getUserStatus method
        // so we'll only assert the first level parameters of the resulting json string.
        assertTextContains("\"addtl_consent\":\"\"".trim())
        assertTextContains("\"consent_string\":\"\"".trim())
        assertTextContains("\"purposes\":{\"legitimate_interest\":{\"enabled\":[".trim())
        assertTextContains("\"vendors\":{\"legitimate_interest\":{\"enabled\":[".trim())
        assertTextContains("\"user_id\":\"".trim())
        assertTextContains("\"created\":\"".trim())
        assertTextContains("\"updated\":\"".trim())
    }
}
