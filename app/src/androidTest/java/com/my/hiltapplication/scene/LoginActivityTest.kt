package com.my.hiltapplication.scene

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.my.hiltapplication.R
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by YourName on 2022/06/20.
 */
@RunWith(AndroidJUnit4::class)
class LoginActivityTest{
    private lateinit var activityScenario : ActivityScenario<LoginActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        this.activityScenario.close()
    }

    @Test
    fun testLogin() {
        val text = "nickname: 12341234, phone:01085708067"
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.et_id)).check(matches(isDisplayed()))
        onView(withId(R.id.et_pw)).check(matches(isDisplayed()))

        onView(withId(R.id.et_id)).perform(typeText("12341234"))
        onView(withId(R.id.et_pw)).perform(typeText("12341234!A"))
        closeSoftKeyboard()
        onView(withId(R.id.btn_login)).perform(click())
        Thread.sleep(5000)

        onView(withId(R.id.et_user_info)).check(matches(Matchers.allOf(isDisplayed(), ViewMatchers.withText(text))))
    }
}