package com.example.cripto_challenge.ui.book_detail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.cripto_challenge.MainActivity
import com.example.cripto_challenge.R
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderBookDetailFragmentTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun checkIfErrorMessageIsHideByDefault() {
        Espresso.onView(ViewMatchers.withId(R.id.orderBookDetailFragment))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }

    fun getIntoFragmentwithData() {
        // Attempt to click first item in RecyclerView.
        val recycler: RecyclerView = rule.activity.findViewById(R.id.recycler_available_books)
        val itemCount = recycler.adapter?.itemCount

        if (itemCount != 0 && itemCount != null){
            Espresso.onView(
                Matchers.allOf(
                    ViewMatchers.isDisplayed(),
                    ViewMatchers.withId(R.id.recycler_available_books)
                )
            )
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        ViewActions.click()
                    ))
        }
    }

    @Test
    @Throws(Exception::class)
    fun checkBidsRecyclerViewVisibility() {
        // Attempt to click first item in RecyclerView.
        val recycler: RecyclerView = rule.activity.findViewById(R.id.recycler_available_books)
        val itemCount = recycler.adapter?.itemCount

        if (itemCount != 0 && itemCount != null){
            Espresso.onView(
                Matchers.allOf(
                    ViewMatchers.isDisplayed(),
                    ViewMatchers.withId(R.id.recycler_available_books)
                )
            )
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        ViewActions.click()
                    ))
        }

        Espresso.onView(ViewMatchers.withId(R.id.recycler_order_bids))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun checkAsksRecyclerViewVisibility() {
        // Attempt to click first item in RecyclerView.
        getIntoFragmentwithData()

        Espresso.onView(ViewMatchers.withId(R.id.recycler_order_asks))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }

}