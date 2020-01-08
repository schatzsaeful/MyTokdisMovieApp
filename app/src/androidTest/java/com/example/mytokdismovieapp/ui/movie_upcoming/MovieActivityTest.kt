package com.example.mytokdismovieapp.ui.movie_upcoming

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.mytokdismovieapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MovieActivity::class.java)

    @Test
    fun testAppBehaviour() {

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Espresso.onView(ViewMatchers.withId(R.id.recyclerMovie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerMovie))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(12))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerMovie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                12,
                ViewActions.click()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.imagePoster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textRelease))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.imageStar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textVoteAverage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textVoteCount))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}