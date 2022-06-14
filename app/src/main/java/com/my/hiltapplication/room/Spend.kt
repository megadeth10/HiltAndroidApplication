package com.my.hiltapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by YourName on 2022/06/14.
 */

@Entity(tableName = "spends")
data class Spend(
    val date : Date,
    val amount : Int,
    val description : String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}