package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed
import org.hamcrest.CoreMatchers.startsWith
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        // EVENT ON_READY NOT SENT ON SUCCESSIVE TESTS,
        // HAVE TO WAIT TO BE SURE THAT THE SDK IS READY
        Thread.sleep(5_000L)

        // Make sure view is ready before starting test
        waitForDisplayed(withText("RESET"))
    }

    @Test
    fun test_GetDisabledPurposes() {
        disagreeToAll()

        tapButton("getDisabledPurposes".toUpperCase())
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetDisabledPurposeIds() {
        disagreeToAll()

        tapButton("getDisabledPurposeIds".toUpperCase())
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetDisabledVendors() {
        disagreeToAll()

        tapButton("getDisabledVendors".toUpperCase())
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetDisabledVendorIds() {
        disagreeToAll()

        tapButton("getDisabledVendorIds".toUpperCase())
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetEnabledPurposes() {
        agreeToAll()

        tapButton("getEnabledPurposes".toUpperCase())
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetEnabledPurposeIds() {
        agreeToAll()

        tapButton("getEnabledPurposeIds".toUpperCase())
        assertText(ALL_PURPOSE_IDS)
    }

    @Test
    fun test_GetEnabledVendors() {
        agreeToAll()

        tapButton("getEnabledVendors".toUpperCase())
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetEnabledVendorIds() {
        agreeToAll()

        tapButton("getEnabledVendorIds".toUpperCase())
        assertText(ALL_VENDOR_IDS)
    }

    @Test
    fun test_GetJavaScriptForWebView() {
        tapButton("getJavaScriptForWebView".toUpperCase())

        val expected = "\"window.didomiOnReady = window.didomiOnReady || [];window.didomiOnReady.push(function (Didomi) {".trim()

        assertTextStartsWith(expected)
    }

    private fun agreeToAll() {
        tapButton("setUserAgreeToAll".toUpperCase())
        Thread.sleep(2_000L)
        assertText("setUserAgreeToAll-OK")
    }

    private fun disagreeToAll() {
        tapButton("setUserDisagreeToAll".toUpperCase())
        Thread.sleep(2_000L)
        assertText("setUserDisagreeToAll-OK")
    }

    private fun tapButton(name: String) {
        val matcher = withText(name)
        onView(matcher).perform(ScrollToAction())
        onView(matcher).perform(click())
    }

    private fun assertText(text: String) {
        val matcher = withText(text)
        onView(matcher).perform(ScrollToAction())
    }

    private fun assertTextStartsWith(text: String) {
        val matcher = withText(startsWith(text))
        onView(matcher).perform(ScrollToAction())
    }

    companion object {
        const val ALL_VENDOR_IDS = "28,google"
        const val ALL_PURPOSE_IDS = "cookies,create_ads_profile,geolocation_data,select_personalized_ads"
    }
}