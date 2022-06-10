package com.my.hiltapplication.store

import android.content.Context
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseStore
import com.my.hiltapplication.callback.IStoreCallback
import com.my.hiltapplication.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserStore  @Inject constructor(@ApplicationContext context : Context) : IStoreCallback, BaseStore() {
    private var user : String? = null

    init {
        this.getTagName(this)
        Log.e(tag, "UserStore init() this:$this")
        this.user = context.resources.getString(R.string.app_name)
    }

    fun getUserStore() = this

    fun setUser(text : String?) {
        this.user = text
    }

    fun getUser() = this.user

    override fun currentState() {
        Log.e(tag, "currentState() this: $this user: $user")
    }

    fun clear() {
        this.user = null
    }
}