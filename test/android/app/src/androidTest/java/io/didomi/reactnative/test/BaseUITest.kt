package io.didomi.reactnative.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.didomi.reactnative.test.EspressoViewFinder.waitForDisplayed
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.core.Is.`is`

/**
 * Class used to contain common logic used by the different test suite classes.
 */
open class BaseUITest {

    protected fun disagreeToAll() {
        tapButton("setUserDisagreeToAll".uppercase())
        Thread.sleep(2_000L)
        assertText("setUserDisagreeToAll-OK")
        scrollToTopOfList()
    }

    private fun scrollToTopOfList() {
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
        onView(matcher).perform(ScrollToAction(), click())
    }

    protected fun scrollToItem(name: String) {
        val matcher = withText(name.uppercase())
        onView(matcher).perform(ScrollToAction())
    }

    protected fun assertText(text: String) {
        val matcher = withText(text)
        onView(matcher).perform(ScrollToAction())
        waitForDisplayed(matcher)
    }

    protected fun assertResult(name: String, expected: String) {
        // Wait for view to be displayed and get its text
        onView(withTagValue(`is`("$name-result")))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.trim())))
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
}
