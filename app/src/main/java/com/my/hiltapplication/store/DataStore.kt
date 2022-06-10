package com.my.hiltapplication.store

import android.content.Context
import android.util.Log
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseStore
import com.my.hiltapplication.callback.IStoreCallback
import javax.inject.Inject

class DataStore  @Inject constructor(context : Context): IStoreCallback, BaseStore() {
    private var data:String? = null


    init {
        this.getTagName(this)
        Log.e(tag, "DataStore init() this:$this")
        this.data = context.resources.getString(R.string.app_name)
    }

    fun getStore() = this

    fun setData(text:String?) = text.also { this.data = it }

    fun getData() = this.data

    fun clear() = null.also { this.data = it }

    override fun currentState() {
        Log.e(tag, "currentState() this: $this data: ${this.data}")
    }
}