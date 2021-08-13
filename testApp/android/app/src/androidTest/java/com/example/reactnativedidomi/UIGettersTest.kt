package com.example.reactnativedidomi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.reactnativedidomi.EspressoViewFinder.waitForDisplayed
import org.junit.After

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UIGettersTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        // EVENT ON_READY NOT SENT ON SUCCESSIVE TESTS,
        // HAVE TO WAIT TO BE SURE THAT THE SDK IS READY
        Thread.sleep(5000L)

        // Make sure view is ready before starting test
        waitForDisplayed(withText("RESET"))
    }

    @After
    fun tearDown() {
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

    private fun agreeToAll() {
        tapButton("setUserAgreeToAll".toUpperCase())
        assertText("setUserAgreeToAll-OK")
    }

    private fun disagreeToAll() {
        tapButton("setUserDisagreeToAll".toUpperCase())
        assertText("setUserDisagreeToAll-OK")
    }

    private fun tapButton(name: String) {
        val matcher = withText(name)
        onView(matcher).perform(scrollTo())
        waitForDisplayed(matcher)
        onView(matcher).perform(click())
    }

    private fun assertText(text: String) {
        val matcher = withText(text)
        onView(matcher).perform(scrollTo())
        waitForDisplayed(matcher)
    }

    companion object {
        const val ALL_VENDOR_IDS = "1,10,100,101,102,104,105,108,109,11,110,111,112,113,114,115,118,119,12,120,122,124,125,126,127,128,129,13,130,131,132,133,134,136,137,138,139,14,140,141,142,143,144,145,147,148,149,15,150,151,152,153,154,155,156,157,158,159,16,160,161,162,163,164,165,167,168,169,17,170,171,173,174,175,177,178,179,18,180,182,183,184,185,188,189,19,190,191,192,193,194,195,196,197,198,199,2,20,200,201,202,203,205,206,208,209,21,210,211,212,213,214,215,216,217,218,22,221,223,224,225,226,227,228,229,23,230,231,232,234,235,236,237,238,239,24,240,241,242,243,244,245,246,248,249,25,250,251,252,253,254,255,256,257,258,259,26,260,261,262,263,264,265,266,268,269,27,270,272,273,274,275,276,277,278,279,28,280,281,282,284,285,287,288,29,290,291,293,294,295,297,298,299,3,30,301,302,303,304,308,31,310,311,312,314,315,316,317,318,319,32,320,321,323,325,326,328,329,33,330,331,333,334,335,336,337,338,339,34,340,341,343,344,345,346,347,349,35,350,351,354,357,358,359,36,360,361,362,365,366,368,369,37,371,373,374,375,376,377,378,38,380,381,382,384,385,387,388,389,39,390,392,394,395,397,398,4,40,400,402,403,404,405,407,408,409,41,410,412,413,415,416,418,42,421,422,423,424,425,426,427,428,429,43,430,434,435,436,438,439,44,440,442,443,444,447,448,449,45,450,452,454,455,458,459,46,461,462,464,465,466,467,468,469,47,471,473,474,475,476,478,479,48,480,482,484,486,487,489,49,490,491,492,493,494,495,496,497,498,499,50,500,501,502,504,505,506,507,509,51,511,512,513,514,515,516,517,518,519,52,520,521,522,523,524,525,527,528,529,53,530,531,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,55,550,551,553,554,556,559,56,560,561,565,568,569,57,570,571,572,573,574,576,577,578,579,58,580,581,584,587,59,590,591,592,593,596,597,598,599,6,60,601,602,604,606,607,608,609,61,610,612,613,614,615,617,618,619,62,620,621,623,624,625,626,627,628,63,631,635,638,639,64,644,645,646,647,648,649,65,650,652,653,654,655,656,657,658,659,66,660,662,663,664,665,666,667,668,67,670,671,672,673,674,675,676,677,678,68,681,682,684,685,686,687,688,69,690,691,697,7,70,702,706,707,708,709,71,712,713,715,716,717,718,719,72,720,721,723,724,728,729,73,731,732,733,734,736,737,738,739,74,740,741,742,743,744,746,747,748,749,75,754,756,758,759,76,760,761,763,764,765,766,768,77,770,773,775,78,79,8,80,81,82,83,84,85,86,87,88,89,9,90,91,92,93,94,95,97,98"
        const val ALL_PURPOSE_IDS = "ad_delivery,advertising_personalization,analytics,content_personalization,cookies"
    }
}