package com.deleo.hiltapplication.base

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

abstract class BaseStore {
    protected var tag: String = BaseStore::class.simpleName ?: ""

    protected fun getTagName(baseClass:BaseStore) {
        this.tag = baseClass::class.simpleName ?: ""
    }
}