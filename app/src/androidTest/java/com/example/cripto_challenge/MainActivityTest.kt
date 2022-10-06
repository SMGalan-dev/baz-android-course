package com.example.cripto_challenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun checkIfErrorMessageIsHideWithNetworkByDefault() {
        onView(withId(R.id.layout_error_ocurred))
            .check(
                matches(
                    withEffectiveVisibility(Visibility.GONE)
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun checkIfErrorMessageIsVisibleWithoutNetwork() {
        onView(withId(R.id.layout_error_ocurred))
            .check(
                matches(
                    isDisplayed()
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun closeErrorMessageWithoutNetwork() {
        //Verify if is visible
        checkIfErrorMessageIsVisibleWithoutNetwork()

        //Click on close icon
        onView(withId(R.id.btn_close_error_message)).perform(click())

        //Verify if is visiblility GONE
        onView(withId(R.id.layout_error_ocurred))
            .check(
                matches(
                    withEffectiveVisibility(Visibility.GONE)
                )
            )
    }

}