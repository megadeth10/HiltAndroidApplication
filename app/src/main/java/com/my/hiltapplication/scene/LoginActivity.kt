package com.my.hiltapplication.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseNetworkActivity
import com.my.hiltapplication.databinding.ActivityLoingBinding
import com.my.hiltapplication.enum.StateStore
import com.my.hiltapplication.util.Log
import com.my.hiltapplication.util.Util
import com.my.hiltapplication.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by YourName on 2022/06/16.
 */

@AndroidEntryPoint
class LoginActivity : BaseNetworkActivity<ActivityLoingBinding>(), View.OnClickListener {
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnLogin.setOnClickListener(this)
        this.contentBinding.btnLogout.setOnClickListener(this)
        this.contentBinding.btnGetUser.setOnClickListener(this)
        this.loginViewModel.progress.observe(this, Observer {
            this.visibleProgress(it)
        })
        this.loginViewModel.resultText.observe(this, Observer {
            this.setResultText(it)
        })
        this.loginViewModel.userInfo.observe(this, Observer {
            this.setUserInfo(it)
        })
    }

    override fun onResume() {
        super.onResume()
        this.checkTokenValid(this.getLogName() ?: "", ::sceneInit)
    }

    private fun sceneInit(state : Int) {
        Log.e(tag, "sceneInit() state: $state")
        if (this.tokenStore.haveUserAuth()) {
            this.contentBinding.btnGetUser.performClick()
        }
    }

    private fun setUserInfo(it : String?) {
        CoroutineScope(Dispatchers.Main).launch {
            this@LoginActivity.contentBinding.etUserInfo.setText(it)
        }
    }

    private fun setResultText(it : String?) {
        CoroutineScope(Dispatchers.Main).launch {
            this@LoginActivity.contentBinding.tvResult.text = it
        }
    }

    private fun visibleProgress(newState : Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            Util.setVisible(this@LoginActivity.contentBinding.flProgress, newState)
        }
    }

    override fun onClick(p0 : View?) {
        when (p0?.id) {
            this.contentBinding.btnLogin.id -> {
                val id = this.contentBinding.etId.text.toString()
                val pw = this.contentBinding.etPw.text.toString()
                this.loginViewModel.getLoginAuth(id, pw)
            }
            this.contentBinding.btnLogout.id -> {
                this.loginViewModel.expiredAuth()
            }
            this.contentBinding.btnGetUser.id -> {
                this.loginViewModel.getUserInfo()
            }
        }
    }

    override fun getContentLayoutId() = R.layout.activity_loing

    override fun getLogName() = LoginActivity::class.simpleName
}