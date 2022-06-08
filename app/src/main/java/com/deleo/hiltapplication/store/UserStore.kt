package com.deleo.hiltapplication.store

import android.content.Context
import android.util.Log
import com.deleo.hiltapplication.R
import com.deleo.hiltapplication.base.BaseStore
import com.deleo.hiltapplication.callback.IStoreCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStore @Inject constructor(@ApplicationContext context : Context) : IStoreCallback, BaseStore() {
//@Module
//@InstallIn(SingletonComponent::class)
//class UserStore(): StoreInterface, BaseStore() {
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

    override fun currentState() {
        Log.e(tag, "currentState() this: $this user: $user")
    }

    fun clear() {
        this.user = null
    }
}