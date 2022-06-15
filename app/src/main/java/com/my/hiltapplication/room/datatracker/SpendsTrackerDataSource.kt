package com.my.hiltapplication.room.datatracker

import com.my.hiltapplication.room.Spend
import com.my.hiltapplication.room.SpendDao

/**
 * Created by YourName on 2022/06/15.
 */
class SpendsTrackerDataSource(
    private val db : SpendDao
) {
    suspend fun addSpend(spend: Spend) = db.addSpend(spend)

    suspend fun getLast20Spends() = db.getSpends()
}