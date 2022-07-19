package com.my.hiltapplication.scene

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.my.hiltapplication.viewmodel.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Rule

/**
 * Created by YourName on 2022/06/20.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class UserInfoActivityTest {

    @get :Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var androidScenario : ActivityScenario<UserInfoActivity>
    private var activity : UserInfoActivity? = null
//    @get: Rule
//    lateinit var activityScenarioRule : ActivityScenarioRule<UserInfoActivity>

    @Before
    fun setUp() {
//        activityScenarioRule = ActivityScenarioRule(UserInfoActivity::class.java)
        androidScenario = launchActivity()
        androidScenario.onActivity {
            activity = it
        }
        androidScenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        androidScenario.close()
        activity = null
    }

    @Test
    fun testUserInfo() {
        println("testUserInfo() start")
        activity?.let {
            val text = "nickname: 12341234, phone:01085708067"
            val result = it.getUserInfo().getOrAwaitValue()
            println("testUserInfo() check result")
            assertThat(text).isEqualTo(result)
//            onView(withId(R.id.et_user_info)).check(matches(allOf(isDisplayed(), withText(text))))
        }
        println("testUserInfo() end")
    }
}