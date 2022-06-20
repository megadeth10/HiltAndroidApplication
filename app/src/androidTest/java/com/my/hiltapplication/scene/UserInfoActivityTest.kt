package com.my.hiltapplication.scene

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.my.hiltapplication.R
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by YourName on 2022/06/20.
 */
@RunWith(AndroidJUnit4::class)
class UserInfoActivityTest{

    private lateinit var androidScenario: ActivityScenario<UserInfoActivity>

//    @get: Rule
//    lateinit var activityScenarioRule : ActivityScenarioRule<UserInfoActivity>

    @Before
    fun setUp() {
        androidScenario = launchActivity()
        androidScenario.moveToState(Lifecycle.State.RESUMED)
//        activityScenarioRule = ActivityScenarioRule(UserInfoActivity::class.java)
    }

    @After
    fun tearDown() {
        androidScenario.close()
    }

    @Test
    fun testUserInfo() {
        val text = "nickname: 12341234, phone:01085708067"
        Thread.sleep(2000)
        onView(withId(R.id.et_user_info)).check(matches(allOf(isDisplayed(), withText(text))))
    }
}