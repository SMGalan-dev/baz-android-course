package com.example.cripto_challenge.ui.available_books

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.cripto_challenge.MainActivity
import com.example.cripto_challenge.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AvailableOrderBooksFragmentTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @Throws(Exception::class)
    fun checkAvailableOrderBooksRecyclerViewVisibility() {
        onView(withId(R.id.recycler_available_books))
            .check(
                matches(
                    isDisplayed()
                )
            )
    }


    @Test
    @Throws(Exception::class)
    fun checkTextViewTopString() {
        onView(withId(R.id.tv_wallet))
            .check(
                matches(
                    withText("Wallet")
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun checkIfErrorMessageIsHideByDefaultWhenIsNetworkAvailable() {
        onView(withId(R.id.layout_error_ocurred))
            .check(
                matches(
                    withEffectiveVisibility(Visibility.GONE)
                )
            )
    }

    @Test
    @Throws(Exception::class)
    fun recyclerHasEthereumDeterminatedData(): Unit = runBlocking {
        // Attempt to scroll all items.
        val recycler: RecyclerView = rule.activity.findViewById(R.id.recycler_available_books)
        val itemCount = recycler.adapter?.itemCount

        if (itemCount != 0 && itemCount != null){
            onView(withId(R.id.recycler_available_books)) // scrollTo will fail the test if no item matches.
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                        itemCount - 1
                    )
                )
        }

        // Match the text in an item below the fold and check that it's displayed.
        val code = "Ethereum"
        onView(
            withText(code)
        ).check(matches(isDisplayed()))

    }

    @Test
    @Throws(Exception::class)
    fun titleExistWhenRecyclerViewItemPressed(): Unit = runBlocking{
        // Attempt to click first item in RecyclerView.
        onView(allOf(isDisplayed(), withId(R.id.recycler_available_books)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.order_book_name)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun bitcoinTitleExistWhenRecyclerViewItemPressed(): Unit = runBlocking{
        // Attempt to click Bitcoin item in RecyclerView.
        onView(allOf(isDisplayed(), withId(R.id.recycler_available_books)))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("Bitcoin")), click()))

        // Match the text in an item below the fold and check that it's displayed.
        //res-name=order_book_name
        val code = "Bitcoin"
        onView(withText(code)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun bitcoinTitleOrderBookNameWhenRecyclerViewItemPressed(): Unit = runBlocking{
        // Attempt to click first item in RecyclerView.
        onView(allOf(isDisplayed(), withId(R.id.recycler_available_books)))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("Bitcoin")), click()))

        val code = "Bitcoin"
        onView(allOf(withText(code), withId(R.id.order_book_name))).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerClickOnFirstItem(): Unit = runBlocking{
        // Attempt to click first item in RecyclerView.
        val recycler: RecyclerView = rule.activity.findViewById(R.id.recycler_available_books)
        val itemCount = recycler.adapter?.itemCount

        if (itemCount != 0 && itemCount != null){
            onView(allOf(isDisplayed(), withId(R.id.recycler_available_books)))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }

        onView(withId(R.id.order_book_name))
            .check(
                matches(isDisplayed())
            )

    }

}