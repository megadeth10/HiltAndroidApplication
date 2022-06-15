package com.my.hiltapplication.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.my.hiltapplication.room.Spend
import com.my.hiltapplication.room.SpendDatabase
import com.my.hiltapplication.room.datatracker.SpendsTrackerDataSource
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by YourName on 2022/06/14.
 */
@RunWith(AndroidJUnit4::class)
class SpendViewModelTest: TestCase() {
    private lateinit var spendViewModel: SpendViewModel
    private lateinit var spendsDatabase: SpendDatabase

    @get :Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        spendsDatabase = Room.inMemoryDatabaseBuilder(context, SpendDatabase::class.java).allowMainThreadQueries().build()
        val dataSource = SpendsTrackerDataSource(spendsDatabase.getSpendDao())
        spendViewModel = SpendViewModel(dataSource)
    }

    @Test
    fun testSpendViewModel() {
        spendViewModel.addSpend(Spend(Date(),170, "test"))
//        spendViewModel.getDbData()
        val result = spendViewModel.dataList.getOrAwaitValue()?.find {
            it.amount == 170 && it.description == "test"
        }
        System.out.println("testSpendViewModel() result: ${result != null}")
        assertThat(result != null).isTrue()
    }

    @After
    public override fun tearDown() {
        super.tearDown()
        spendsDatabase.close()
    }
}