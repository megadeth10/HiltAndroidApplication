package com.my.hiltapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by YourName on 2022/06/14.
 */
@Database(entities = [Spend::class], version = 1)
@TypeConverters(SpendDataConverter::class)
abstract class SpendDatabase : RoomDatabase() {
    abstract fun getSpendDao() : SpendDao

    companion object {
        private const val DB_NAME = "Spends-Database.db"

        @Volatile
        private var instance:SpendDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context:Context, dbFileName: String = DB_NAME): SpendDatabase {
            instance ?: buildDatabase(context, dbFileName).apply {
                instance = this.build()
            }
            return instance!!
        }

        private fun buildDatabase(context : Context, dbFileName : String) : Builder<SpendDatabase> {
            return Room.databaseBuilder(context, SpendDatabase::class.java, dbFileName)
        }
    }
}