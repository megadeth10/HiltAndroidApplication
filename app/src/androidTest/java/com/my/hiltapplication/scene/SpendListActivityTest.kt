package com.my.hiltapplication.scene

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import com.my.hiltapplication.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by YourName on 2022/06/15.
 */

@LargeTest // 시간 베이스로 테스트 volume이 클때
@RunWith(AndroidJUnit4::class)
class SpendListActivityTest {
    private lateinit var activityScenario: ActivityScenario<SpendListActivity>

    @Before
    fun setUp() {
        activityScenario = launchActivity()
        activityScenario.moveToState(Lifecycle.State.RESUMED)
    }
//
//    @After
//    fun tearDown() {
//    }

    @Test
    fun testLaunchActivity() {
        val scenario = launchActivity<SpendListActivity>()
        scenario.moveToState(Lifecycle.State.CREATED)
        assertThat(scenario.state == Lifecycle.State.CREATED).isTrue()
        scenario.moveToState(Lifecycle.State.RESUMED)
        assertThat(scenario.state == Lifecycle.State.RESUMED).isTrue()

    }

    @Test
    fun testAddSpendItem() {
        val amount = 12
        val description = "hie"
        onView(withId(R.id.et_amount)).perform(typeText(amount.toString()))
        onView(withId(R.id.et_description)).perform(typeText(description))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_action)).perform(click())
        onView(withText(amount.toString())).check(matches(isDisplayed()))
        onView(withText(description)).check(matches(isDisplayed()))
//        assertThat(onView(withId(R.id.et_description)).check(matches(withText(description)))).isEqualTo(true)
    }
}