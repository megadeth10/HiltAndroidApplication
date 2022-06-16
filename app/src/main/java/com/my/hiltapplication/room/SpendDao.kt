package com.my.hiltapplication.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by YourName on 2022/06/14.
 */
@Dao
interface SpendDao {
    @Insert
    suspend fun addSpend(spend : Spend)

    @Query("SELECT * FROM spends ORDER BY date DESC LIMIT 20")
    suspend fun getSpends() : List<Spend>

    @Delete
    suspend fun removeAll(list: List<Spend>)
}