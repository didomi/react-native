package com.example.reactnativedidomi

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UISetUserTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
        tapButton("reset")
        tapButton("clearUser")
    }

    @Test
    fun test_ClearUser() {
        tapButton("clearUser")
        Thread.sleep(2_000L)
        assertText("clearUser-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithId() {
        tapButton("setUserWithId")
        Thread.sleep(2_000L)
        assertText("setUserWithId-OK")
    }

    // TODO Tests are ignored because of a OutOfMemoryError happening only on Firebase, to be investigated

    @Test
    @Ignore
    fun test_SetUserWithIdAndSetupUI() {
        tapButton("setUserWithIdAndSetupUI")
        Thread.sleep(2_000L)
        assertText("setUserWithIdAndSetupUI-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithHashAuth() {
        tapButton("setUserWithHashAuth")
        Thread.sleep(2_000L)
        assertText("setUserWithHashAuth-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithHashAuthAndSetupUI() {
        tapButton("setUserWithHashAuthAndSetupUI")
        Thread.sleep(2_000L)
        assertText("setUserWithHashAuthAndSetupUI-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithHashAuthWithSaltAndExpiration() {
        tapButton("setUserWithHashAuthWithSaltAndExpiration")
        Thread.sleep(2_000L)
        assertText("setUserWithHashAuthWithSaltAndExpiration-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithHashAuthWithSaltAndExpirationAndSetupUI() {
        tapButton("setUserWithHashAuthWithSaltAndExpirationAndSetupUI")
        Thread.sleep(2_000L)
        assertText("setUserWithHashAuthWithSaltAndExpirationAndSetupUI-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithEncryptionAuth() {
        tapButton("setUserWithEncryptionAuth")
        Thread.sleep(2_000L)
        assertText("setUserWithEncryptionAuth-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithEncryptionAuthAndSetupUI() {
        tapButton("setUserWithEncryptionAuthAndSetupUI")
        Thread.sleep(2_000L)
        assertText("setUserWithEncryptionAuthAndSetupUI-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithEncryptionAuthWithExpiration() {
        tapButton("setUserWithEncryptionAuthWithExpiration")
        Thread.sleep(2_000L)
        // Prevent random test failure
        scrollToBottomOfList()
        Thread.sleep(1_000L)
        assertText("setUserWithEncryptionAuthWithExpiration-OK")
    }

    @Test
    @Ignore
    fun test_SetUserWithEncryptionAuthWithExpirationAndSetupUI() {
        tapButton("setUserWithEncryptionAuthWithExpirationAndSetupUI")
        Thread.sleep(2_000L)
        assertText("setUserWithEncryptionAuthWithExpirationAndSetupUI-OK")
    }
}
