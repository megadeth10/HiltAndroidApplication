package com.my.hiltapplication

/**
 * Created by YourName on 2022/06/14.
 */
object Validator {
    fun validateInput(amount:Int, desc:String): Boolean {
        return !(amount <=0 || desc.isEmpty())
    }
}