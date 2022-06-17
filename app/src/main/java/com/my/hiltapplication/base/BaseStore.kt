package com.my.hiltapplication.base

import com.my.hiltapplication.enum.StateStore

abstract class BaseStore {
    protected var tag: String = BaseStore::class.simpleName ?: ""
    protected var storeState = StateStore.None
    protected fun getTagName(baseClass:BaseStore) {
        this.tag = baseClass::class.simpleName ?: ""
    }

    fun getState() = this.storeState
}