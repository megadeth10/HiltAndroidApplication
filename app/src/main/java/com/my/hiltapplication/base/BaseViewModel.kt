package com.my.hiltapplication.base

import androidx.lifecycle.ViewModel
import com.my.hiltapplication.callback.ILogCallback

abstract class BaseViewModel: ViewModel(), ILogCallback {
    protected var tagName: String = BaseViewModel::class.simpleName ?: ""
    init {
        tagName = this.getLogName() ?: ""
    }
}