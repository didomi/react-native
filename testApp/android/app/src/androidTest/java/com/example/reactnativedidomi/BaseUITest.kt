package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.startsWith

open class BaseUITest {
    protected fun agreeToAll() {
        tapButton("setUserAgreeToAll".toUpperCase())
        Thread.sleep(2_000L)
        assertText("setUserAgreeToAll-OK")
    }

    protected fun disagreeToAll() {
        tapButton("setUserDisagreeToAll".toUpperCase())
        Thread.sleep(2_000L)
        assertText("setUserDisagreeToAll-OK")
    }

    protected fun tapButton(name: String) {
        val matcher = withText(name.toUpperCase())
        onView(matcher).perform(ScrollToAction())
        onView(matcher).perform(ViewActions.click())
    }

    protected fun assertText(text: String) {
        val matcher = withText(text)
        onView(matcher).perform(ScrollToAction())
    }

    protected fun assertTextStartsWith(text: String) {
        val matcher = withText(startsWith(text))
        onView(matcher).perform(ScrollToAction())
    }

    companion object {
        const val ALL_VENDOR_IDS = "28,google"
        const val ALL_PURPOSE_IDS = "cookies,create_ads_profile,geolocation_data,select_personalized_ads"
    }
}