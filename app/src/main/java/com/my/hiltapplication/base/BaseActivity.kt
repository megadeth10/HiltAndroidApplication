package com.my.hiltapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.my.hiltapplication.callback.IActivityCallback
import com.my.hiltapplication.callback.ILogCallback
import com.my.hiltapplication.util.Log

abstract class BaseActivity<V: ViewDataBinding>: AppCompatActivity(), IActivityCallback, ILogCallback {

//    @Inject
//    lateinit var userStore : UserStore
//
//    @Inject
//    lateinit var dataStore : DataStore

    protected var tag = BaseActivity::class.simpleName
    protected lateinit var contentBinding: V

    init {
        tag = this.getLogName() ?: ""
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.setViewDataBinding()
        Log.e(tag, "onCreate()")
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setViewDataBinding() {
        this.contentBinding = DataBindingUtil.setContentView(this, this.getContentLayoutId())
        this.contentBinding.lifecycleOwner = this
    }

}