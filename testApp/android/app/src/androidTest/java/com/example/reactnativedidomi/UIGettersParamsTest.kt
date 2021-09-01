package com.example.reactnativedidomi

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersParamsTest: BaseUITest() {

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

    //TODO FIND A WAY TO CHECK WITHOUT THE ID HARD SET
    @Test
    fun test_GetPurpose() {
        // This assertion might be fragile if Android doesn't always keep the same order for the properties.
        val expected = """
            {
            "descriptionLegal":"purpose_1_description_legal",
            "iabId":"1",
            "description":"purpose_1_description",
            "name":"purpose_1_name",
            "id":"cookies"
            }
        """.trimIndent().replace("\n","")

        tapButton("getPurpose [ID = 'cookies']")
        assertText(expected)
    }

    @Test
    fun test_GetText() {
        tapButton("getText [Key = '0']")
        assertText(text = "{}", accessibilityLabel = "getText [Key = '0']-result")
    }

    @Test
    fun test_GetTranslatedText() {
        tapButton("getTranslatedText [Key = '0']")
        assertText(text = "0", accessibilityLabel = "getTranslatedText [Key = '0']-result")
    }

    @Test
    fun test_GetUserConsentStatusForPurpose() {
        agreeToAll()

        tapButton("getUserConsentStatusForPurpose [ID = 'cookies']")
        assertText(text = "true", accessibilityLabel = "getUserConsentStatusForPurpose [ID = 'cookies']-result")
    }

    @Test
    fun test_GetUserConsentStatusForVendor() {
        agreeToAll()

        tapButton("getUserConsentStatusForVendor [ID = '0']")
        assertText(text = "{}", accessibilityLabel = "getUserConsentStatusForVendor [ID = '0']-result")
    }

    @Test
    fun test_GetUserConsentStatusForVendorAndRequiredPurposes() {
        agreeToAll()

        tapButton("getUserConsentStatusForVendorAndRequiredPurposes [ID = '755']")
        assertText(text = "true", accessibilityLabel = "getUserConsentStatusForVendorAndRequiredPurposes [ID = '755']-result")
    }

    @Test
    fun test_GetEnabledVendors() {
        agreeToAll()

        tapButton("getUserLegitimateInterestStatusForPurpose [ID = 'cookies']")
        assertText(text = "true", accessibilityLabel = "getUserLegitimateInterestStatusForPurpose [ID = 'cookies']-result")
    }
}