package com.example.reactnativedidomi

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class UISettersTest: BaseUITest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        waitForSdkToBeReady()
    }

    @Test
    fun test_SetUserStatusSets() {
        tapButton("setUserStatusSets")
        Thread.sleep(2_000L)
        assertText("setUserStatusSets-OK")
    }

    @Test
    fun test_SetUserAgreeToAll() {
        tapButton("setUserAgreeToAll")
        Thread.sleep(2_000L)
        assertText("setUserAgreeToAll-OK")
    }

    @Test
    fun test_SetUserDisagreeToAll() {
        tapButton("setUserDisagreeToAll")
        Thread.sleep(2_000L)
        assertText("setUserDisagreeToAll-OK")
    }

    @Test
    fun test_SetUserWithId() {
        tapButton("setUserWithId")
        Thread.sleep(2_000L)
        assertText("setUserWithId-OK")
    }

    @Test
    fun test_SetUserWithHashAuth() {
        tapButton("setUserWithHashAuth")
        Thread.sleep(2_000L)
        assertText("setUserWithHashAuth-OK")
    }

    @Test
    fun test_SetUserWithHashAuthWithSaltAndExpiration() {
        tapButton("setUserWithHashAuthWithSaltAndExpiration")
        Thread.sleep(2_000L)
        assertText("setUserWithHashAuthWithSaltAndExpiration-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuth() {
        tapButton("setUserWithEncryptionAuth")
        Thread.sleep(2_000L)
        assertText("setUserWithEncryptionAuth-OK")
    }

    @Test
    fun test_SetUserWithEncryptionAuthWithExpiration() {
        tapButton("setUserWithEncryptionAuthWithExpiration")
        Thread.sleep(2_000L)
        assertText("setUserWithEncryptionAuthWithExpiration-OK")
    }
}
