package com.my.hiltapplication.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.my.hiltapplication.store.TokenStore
import javax.inject.Inject

/**
 * Created by YourName on 2022/06/16.
 */
abstract class BaseNetworkActivity<V : ViewDataBinding> : BaseAlertActivity<V>() {
    @Inject
    lateinit var tokenStore : TokenStore
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * token Store 상태 체크
     */
    protected fun checkTokenValid(callName : String, callFunction : (Int) -> Unit) {
        tokenStore.checkValidationToken(callName, callFunction)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }
}