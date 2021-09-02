package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.startsWith

/**
 * Class used to contain common logic used by the different test suite classes.
 */
open class BaseUITest {
    protected fun agreeToAll() {
        tapButton("setUserAgreeToAll".toUpperCase())
        Thread.sleep(2_000L)
        assertText("setUserAgreeToAll-OK")
        scrollToTopOfList()
    }

    protected fun disagreeToAll() {
        tapButton("setUserDisagreeToAll".toUpperCase())
        Thread.sleep(2_000L)
        assertText("setUserDisagreeToAll-OK")
        scrollToTopOfList()
    }

    private fun scrollToTopOfList() {
        // We scroll back up because scrolling up does not work as expected on all elements.
        // We do seem to be able to scroll properly to these text views so we scroll up to the first one (METHODS).
        scrollToItem("METHODS")
    }

    protected fun tapButton(name: String) {
        val matcher = withText(name.toUpperCase())
        onView(matcher).perform(ScrollToAction())
        onView(matcher).perform(ViewActions.click())
    }

    private fun scrollToItem(name: String) {
        val matcher = withText(name.toUpperCase())
        onView(matcher).perform(scrollTo())
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

    protected fun waitForSdkToBeReady() {
        waitForDisplayed(withText("SDK STATUS: READY"))
    }

    companion object {
        // Considering the tests that we do for the bridge SDKs, using simple a configuration with few vendors
        // should be enough. Currently we share the same vendor and purpose configuration across tests classes.
        const val ALL_VENDOR_IDS = "28,google"
        const val ALL_PURPOSE_IDS = "cookies,create_ads_profile,geolocation_data,select_personalized_ads"
    }
}
