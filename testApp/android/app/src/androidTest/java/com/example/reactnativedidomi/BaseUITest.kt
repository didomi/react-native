package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.startsWith

/**
 * Class used to contain common logic used by the different test suite classes.
 */
open class BaseUITest {
    protected fun agreeToAll() {
        tapButton("setUserAgreeToAll".uppercase())
        Thread.sleep(2_000L)
        assertText("setUserAgreeToAll-OK")
        scrollToTopOfList()
    }

    protected fun disagreeToAll() {
        tapButton("setUserDisagreeToAll".uppercase())
        Thread.sleep(2_000L)
        assertText("setUserDisagreeToAll-OK")
        scrollToTopOfList()
    }

    protected fun scrollToTopOfList() {
        // We scroll back up because scrolling up does not work as expected on all elements.
        // We do seem to be able to scroll properly to these text views so we scroll up to the first one (METHODS).
        scrollToItem("METHODS")
    }

    protected fun scrollToBottomOfList() {
        // We scroll at the bottom because scrolling down does not work as expected on all elements.
        scrollToItem("SETUSERWITHENCRYPTIONAUTHWITHEXPIRATIONANDSETUPUI")
    }

    protected fun tapButton(name: String) {
        val matcher = withText(name.uppercase())
        onView(matcher).perform(ScrollToAction())
        onView(matcher).perform(click())
    }

    private fun scrollToItem(name: String) {
        val matcher = withText(name.uppercase())
        onView(matcher).perform(ScrollToAction())
    }

    protected fun assertText(text: String) {
        val matcher = withText(text)
        onView(matcher).perform(ScrollToAction())
    }

    protected fun assertTextStartsWith(text: String) {
        val matcher = withText(startsWith(text))
        onView(matcher).perform(ScrollToAction())
    }

    protected fun assertTextEndsWith(text: String) {
        val matcher = withText(endsWith(text))
        onView(matcher).perform(ScrollToAction())
    }

    protected fun assertTextContains(text: String) {
        val matcher = withSubstring(text)
        onView(matcher).perform(ScrollToAction())
    }

    protected fun waitForSdkToBeReady() {
        waitForDisplayed(withText("SDK STATUS: READY"))
    }

    companion object {
        // Considering the tests that we do for the bridge SDKs, using simple a configuration with few vendors
        // should be enough. Currently we share the same vendor and purpose configuration across tests classes.
        const val ALL_VENDOR_IDS = "1111,217,272"
        const val ALL_PURPOSE_IDS = "cookies,create_ads_profile,device_characteristics,geolocation_data," +
                "improve_products,market_research,measure_ad_performance,measure_content_performance," +
                "select_basic_ads,select_personalized_ads,use_limited_data_to_select_content"
    }
}
