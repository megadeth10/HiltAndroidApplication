package com.deleo.hiltapplication.base

import androidx.lifecycle.ViewModel
import com.deleo.hiltapplication.callback.ILogCallback

abstract class BaseViewModel: ViewModel(), ILogCallback {
    protected var tagName: String = BaseViewModel::class.simpleName ?: ""
    init {
        tagName = this.getLogName() ?: ""
    }
}