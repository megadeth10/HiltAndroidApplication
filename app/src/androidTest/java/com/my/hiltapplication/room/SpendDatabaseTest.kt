package com.my.hiltapplication.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.my.hiltapplication.room.datatracker.SpendsTrackerDataSource
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by YourName on 2022/06/14.
 */
@RunWith(AndroidJUnit4::class) // AndroidJUnit4이 build variant가 debug로해야 확임됨
class SpendDatabaseTest: TestCase() {

    private lateinit var db: SpendDatabase
    private lateinit var dao: SpendDao

    @Before
    fun aaaa() {
        System.out.println("call aaaa()")
    }

    @Before
    public override fun setUp() {
        System.out.println("call setUp()")
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SpendDatabase::class.java).build()
        dao = db.getSpendDao()

    }

    @After
    fun closeDb() {
        System.out.println("call closeDb()")
        db.close()
    }

    @After
    public override fun tearDown() {
        System.out.println("call tearDown()")
    }

    @Test
    fun writeAndReadSpend() = runBlocking{
        val date = Date()
        val spend = Spend(date, 1000, "test date")
        dao.addSpend(spend)
        val spends = dao.getSpends()
        for(item in spends) {
            System.out.println("date: ${item.date} amount: ${item.amount} description: ${item.description}")
        }
        assertThat(spends.contains(spend)).isTrue()
    }
}