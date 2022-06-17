package com.my.hiltapplication.scene

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseNetworkActivity
import com.my.hiltapplication.databinding.ActivityUserInfoBinding
import com.my.hiltapplication.util.Log
import com.my.hiltapplication.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by YourName on 2022/06/17.
 */
@AndroidEntryPoint
class UserInfoActivity : BaseNetworkActivity<ActivityUserInfoBinding>(), View.OnClickListener {
    private val userInfoViewModel : UserInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        this.contentBinding.btnGetUser.setOnClickListener(this)

        this.userInfoViewModel.userInfo.observe(this, Observer {
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
            this@UserInfoActivity.contentBinding.etUserInfo.setText(it)
        }
    }

    override fun getContentLayoutId() = R.layout.activity_user_info

    override fun getLogName() = UserInfoActivity::class.simpleName
    override fun onClick(p0 : View?) {
        when (p0?.id) {
            this.contentBinding.btnGetUser.id -> {
                this.userInfoViewModel.getUserInfo()
            }
        }
    }
}