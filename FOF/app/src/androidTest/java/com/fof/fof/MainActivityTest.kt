package com.fof.fof

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented UI tests for MainActivity.
 *
 * Run against a connected device or emulator:
 *   .\gradlew.bat connectedAndroidTest
 *
 * Note: Tests that require the accessibility service to be active (i.e. actually
 * opening the system power menu) cannot be automated — they are covered by the
 * manual pre-release checklist in README.md.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun powerMenuCardIsDisplayed() {
        onView(withId(R.id.card_power_menu))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whyFoffButtonIsDisplayed() {
        onView(withId(R.id.btn_why_foff))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whyFoffDialogOpensAndShowsTitle() {
        onView(withId(R.id.btn_why_foff)).perform(click())
        onView(withText("FOFF – Feckin off!"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whyFoffDialogDismissesOnPositiveButton() {
        onView(withId(R.id.btn_why_foff)).perform(click())
        onView(withText("Feckin right")).perform(click())
        // Dialog dismissed — card should be visible again
        onView(withId(R.id.card_power_menu))
            .check(matches(isDisplayed()))
    }
}
