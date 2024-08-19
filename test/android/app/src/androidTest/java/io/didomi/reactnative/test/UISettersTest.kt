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
class UISettersTest : BaseUITest() {

    companion object {
        private const val DELAY = 2_000L
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() = waitForSdkToBeReady()

    @Test
    fun test_SetCurrentUserStatusFails() {
        tapButton("setCurrentUserStatus-Fails")
        Thread.sleep(DELAY)
        assertText("\"setCurrentUserStatus-Fails-false\"")
    }

    @Test
    fun test_SetCurrentUserStatusSucceeds() {
        tapButton("setCurrentUserStatus-Succeeds")
        Thread.sleep(DELAY)
        assertText("\"setCurrentUserStatus-Succeeds-true\"")
    }

    @Test
    fun test_SetUserStatusSets() {
        tapButton("setUserStatusSets")
        Thread.sleep(DELAY)
        assertText("setUserStatusSets-OK")
    }

    @Test
    fun test_SetUserAgreeToAll() {
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        assertText("setUserAgreeToAll-OK")
    }

    @Test
    fun test_SetUserDisagreeToAll() {
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        assertText("setUserDisagreeToAll-OK")
    }

    /** Single purposes */
    @Test
    fun test_EnablePurposeTransactionWithChange() {
        val buttonName = "enablePurpose[cookies]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-true\"")
    }

    @Test
    fun test_EnablePurposeTransactionWithoutChange() {
        val buttonName = "enablePurpose[cookies]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-true\"")
    }

    @Test
    fun test_DisablePurposeTransactionWithChange() {
        val buttonName = "disablePurpose[cookies]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-false\"")
    }

    @Test
    fun test_DisablePurposeTransactionWithoutChange() {
        val buttonName = "disablePurpose[cookies]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-false\"")
    }

    /** Multiple purposes */
    @Test
    fun test_EnablePurposesTransactionWithChange() {
        val buttonName = "enablePurposes[cookies]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-true\"")
    }

    @Test
    fun test_EnablePurposesTransactionWithoutChange() {
        val buttonName = "enablePurposes[cookies]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-true\"")
    }

    @Test
    fun test_DisablePurposesTransactionWithChange() {
        val buttonName = "disablePurposes[cookies]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-false\"")
    }

    @Test
    fun test_DisablePurposesTransactionWithoutChange() {
        val buttonName = "disablePurposes[cookies]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-false\"")
    }

    /** Single vendors */
    @Test
    fun test_EnableVendorTransactionWithChange() {
        val buttonName = "enableVendor[ipromote]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-true\"")
    }

    @Test
    fun test_EnableVendorTransactionWithoutChange() {
        val buttonName = "enableVendor[ipromote]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-true\"")
    }

    @Test
    fun test_DisableVendorTransactionWithChange() {
        val buttonName = "disableVendor[ipromote]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-false\"")
    }

    @Test
    fun test_DisableVendorTransactionWithoutChange() {
        val buttonName = "disableVendor[ipromote]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-false\"")
    }

    /** Multiple vendors */
    @Test
    fun test_EnableVendorsTransactionWithChange() {
        val buttonName = "enableVendors[ipromote]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-true\"")
    }

    @Test
    fun test_EnableVendorsTransactionWithoutChange() {
        val buttonName = "enableVendors[ipromote]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-true\"")
    }

    @Test
    fun test_DisableVendorsTransactionWithChange() {
        val buttonName = "disableVendors[ipromote]-transaction"
        tapButton("setUserAgreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-true-enabled-false\"")
    }

    @Test
    fun test_DisableVendorsTransactionWithoutChange() {
        val buttonName = "disableVendors[ipromote]-transaction"
        tapButton("setUserDisagreeToAll")
        Thread.sleep(DELAY)
        tapButton(buttonName)
        Thread.sleep(DELAY)
        assertText("\"${buttonName}-updated-false-enabled-false\"")
    }
}
