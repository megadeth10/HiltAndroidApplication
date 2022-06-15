package com.my.hiltapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by YourName on 2022/06/15.
 */

fun <T> LiveData<T>.getOrAwaitValue() : T {
    var data : T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(t : T) {
            data = t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)
    try {
        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw TimeoutException("Live Data never get")
        }
    } finally {
        this.removeObserver(observer)
    }
    return data as T
}