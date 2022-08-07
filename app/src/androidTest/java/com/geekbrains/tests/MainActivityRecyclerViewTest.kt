package com.geekbrains.tests

import TEST_FAKE_SEARCH
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.SearchResultAdapter.SearchResultViewHolder
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_ScrollTo() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            fakeLoadListTest()

            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.scrollTo<SearchResultViewHolder>(
                        hasDescendant(withText("FullName: 42"))
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickAtPosition() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            fakeLoadListTest()

            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<SearchResultViewHolder>(
                        0,
                        click()
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickOnItem() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            fakeLoadListTest()

            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.scrollTo<SearchResultViewHolder>(
                        hasDescendant(withText("FullName: 50"))
                    )
                )

            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItem<SearchResultViewHolder>(
                        hasDescendant(withText("FullName: 42")),
                        click()
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformCustomClick() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            fakeLoadListTest()

            onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions
                        .actionOnItemAtPosition<SearchResultViewHolder>(
                            0,
                            tapOnItemWithId(R.id.checkbox)
                        )
                )
        }
    }

    private fun fakeLoadListTest() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText(TEST_FAKE_SEARCH),
            closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Нажимаем на view с указанным id"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()
        }
    }

    @Test
    fun activitySearch_RealScrollToPosition() {
        realLoadListTest()
        onView(isRoot()).perform(delay())
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<SearchResultViewHolder>(77)
            )
    }

    @Test
    fun activitySearch_RealClickPosition() {
        realLoadListTest()
        onView(isRoot()).perform(delay())
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<SearchResultViewHolder>(
                    0,
                    click()
                )
            )
    }

    private fun realLoadListTest() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("RecyclerView"),
            closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $3 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(3000)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}