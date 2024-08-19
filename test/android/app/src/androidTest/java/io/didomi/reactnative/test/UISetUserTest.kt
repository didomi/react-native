package io.didomi.reactnative.test

import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UISetUserTest : BaseUITest() {

    companion object {
        private const val DELAY = 2_000L
    }

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
        Thread.sleep(DELAY)
        assertText("clearUser-OK")
    }

    @Test
    fun test_SetUserWithId() {
        tapButton("setUserWithId")
        Thread.sleep(DELAY)
        assertText("setUserWithId-OK")
    }

    @Test
    fun test_SetUserWithIdAndSetupUI() {
        tapButton("setUserWithIdAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithIdAndSetupUI-OK")
    }

    @Test
    fun test_SetUserWithHashAuth() {
        tapButton("setUserWithHashAuth")
        Thread.sleep(DELAY)
        assertText("setUserWithHashAuth-OK")
    }

    @Test
    fun test_SetUserWithHashAuthAndSetupUI() {
        tapButton("setUserWithHashAuthAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithHashAuthAndSetupUI-OK")
    }

    @Test
    fun test_SetUserWithHashAuthWithSaltAndExpiration() {
        tapButton("setUserWithHashAuthWithSaltAndExpiration")
        Thread.sleep(DELAY)
        assertText("setUserWithHashAuthWithSaltAndExpiration-OK")
    }

    @Test
    fun test_SetUserWithHashAuthWithSaltAndExpirationAndSetupUI() {
        tapButton("setUserWithHashAuthWithSaltAndExpirationAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithHashAuthWithSaltAndExpirationAndSetupUI-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuth() {
        tapButton("setUserWithEncryptionAuth")
        Thread.sleep(DELAY)
        assertText("setUserWithEncryptionAuth-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuthAndSetupUI() {
        tapButton("setUserWithEncryptionAuthAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithEncryptionAuthAndSetupUI-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuthWithExpiration() {
        tapButton("setUserWithEncryptionAuthWithExpiration")
        Thread.sleep(DELAY)
        // Prevent random test failure
        scrollToBottomOfList()
        Thread.sleep(1_000L)
        assertText("setUserWithEncryptionAuthWithExpiration-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuthWithExpirationAndSetupUI() {
        tapButton("setUserWithEncryptionAuthWithExpirationAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithEncryptionAuthWithExpirationAndSetupUI-OK")
    }

    @Test
    fun test_SetUserWithHashAuthWithSynchronizedUsers() {
        tapButton("setUserWithHashAuthWithSynchronizedUsers")
        Thread.sleep(DELAY)
        assertText("setUserWithHashAuthWithSynchronizedUsers-OK")
    }

    @Test
    fun test_SetUserWithHashAuthWithSynchronizedUsersAndSetupUI() {
        tapButton("setUserWithHashAuthWithSynchronizedUsersAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithHashAuthWithSynchronizedUsersAndSetupUI-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuthWithSynchronizedUsers() {
        tapButton("setUserWithEncryptionAuthWithSynchronizedUsers")
        Thread.sleep(DELAY)
        assertText("setUserWithEncryptionAuthWithSynchronizedUsers-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI() {
        tapButton("setUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI")
        Thread.sleep(DELAY)
        assertText("setUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI-OK")
    }

    @Test
    fun test_SyncReadyEvent() {
        tapButton("Listen user sync")
        EspressoViewFinder.waitForDisplayed(withText("Listen user sync-OK"))

        tapButton("setUserWithId")
        Thread.sleep(DELAY)
        assertText("setUserWithId-OK")

        EspressoViewFinder.waitForDisplayed(
            withText(
                "LAST RECEIVED EVENT: Sync Ready, status applied? true, acknowledged? true"
            )
        )

        tapButton("Restore event listeners")
        EspressoViewFinder.waitForDisplayed(withText("Restore event listeners-OK"))
    }
}
