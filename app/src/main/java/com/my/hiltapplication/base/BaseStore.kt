package com.my.hiltapplication.base

abstract class BaseStore {
    protected var tag: String = BaseStore::class.simpleName ?: ""

    protected fun getTagName(baseClass:BaseStore) {
        this.tag = baseClass::class.simpleName ?: ""
    }
}