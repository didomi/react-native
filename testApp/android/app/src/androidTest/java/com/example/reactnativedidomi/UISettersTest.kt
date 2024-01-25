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
    fun test_SetCurrentUserStatusFails() {
        tapButton("setCurrentUserStatus-Fails")
        Thread.sleep(2_000L)
        assertText("\"setCurrentUserStatus-Fails-false\"")
    }

    @Test
    fun test_SetCurrentUserStatusSucceeds() {
        tapButton("setCurrentUserStatus-Succeeds")
        Thread.sleep(2_000L)
        assertText("\"setCurrentUserStatus-Succeeds-true\"")
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
}
