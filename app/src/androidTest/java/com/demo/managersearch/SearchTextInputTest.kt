package com.demo.managersearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTextInputTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launchActivity()
    }

    @Test
    fun testSearchInputText() {

        onView(withId(R.id.search_field)).perform(click())
        onView(withId(R.id.search_field)).perform(typeText("ha"))
        Thread.sleep(2000) // todo mock repository instead
        onView(withId(R.id.manager_list)).check(RecyclerViewItemCountAssertion(2))

        onView(withId(R.id.search_field)).perform(clearText())
        Thread.sleep(2000) // todo mock repository instead
        onView(withId(R.id.manager_list)).check(RecyclerViewItemCountAssertion(0))
        onView(withId(R.id.empty_state_text)).check(matches(not(isDisplayed())))

        onView(withId(R.id.search_field)).perform(typeText("Ha"))
        Thread.sleep(2000) // todo mock repository instead
        onView(withId(R.id.manager_list)).check(RecyclerViewItemCountAssertion(2))

        onView(withId(R.id.search_field)).perform(replaceText("manager"))
        Thread.sleep(2000) // todo mock repository instead
        onView(withId(R.id.manager_list)).check(RecyclerViewItemCountAssertion(1))

        onView(withId(R.id.search_field)).perform(replaceText("some random long name"))
        Thread.sleep(2000) // todo mock repository instead
        onView(withId(R.id.manager_list)).check(RecyclerViewItemCountAssertion(0))
        onView(withId(R.id.empty_state_text)).check(matches(isDisplayed()))

    }
}

class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, `is`(expectedCount))
    }
}