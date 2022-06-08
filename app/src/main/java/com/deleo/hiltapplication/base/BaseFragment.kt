package com.deleo.hiltapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deleo.hiltapplication.callback.IActivityCallback
import com.deleo.hiltapplication.callback.ILogCallback

abstract class BaseFragment: Fragment(), IActivityCallback, ILogCallback {
    private var logName = ""
    override fun getLogName() = BaseFragment::class.simpleName

    init {
        logName = this.getLogName() ?: ""
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        val viewId = this.getContentLayoutId()
        return inflater.inflate(viewId, container, false)
    }
}