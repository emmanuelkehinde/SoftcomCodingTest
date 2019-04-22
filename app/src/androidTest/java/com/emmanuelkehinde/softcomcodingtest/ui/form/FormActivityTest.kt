package com.emmanuelkehinde.softcomcodingtest.ui.form


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.ui.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FormActivityTest {

    @Rule
    @JvmField
    var mMainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        onView(withId(R.id.btn_proceed)).perform(click()) //Go to FormActivity
    }

    @Test
    fun pageTitleIsDisplayed() {
        val tvPageTitle = onView(withId(R.id.tv_page_title))
        tvPageTitle.check(matches(isDisplayed()))
    }

    @Test
    fun nextButtonIsDisplayed() {
        val btnNext = onView(withId(R.id.btn_next_page))
        btnNext.check(matches(isDisplayed()))
    }

    @Test
    fun progressBarIsDisplayed() {
        val progressBar = onView(withId(R.id.progress_bar))
        progressBar.check(matches(isDisplayed()))
    }
}
