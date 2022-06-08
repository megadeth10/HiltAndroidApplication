package com.deleo.hiltapplication.store

import android.content.Context
import android.util.Log
import com.deleo.hiltapplication.R
import com.deleo.hiltapplication.base.BaseStore
import com.deleo.hiltapplication.callback.IStoreCallback
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

//@Module
//@InstallIn(ActivityRetainedComponent::class)
@Singleton
class DataStore @Inject constructor(@ApplicationContext context : Context): IStoreCallback, BaseStore() {
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