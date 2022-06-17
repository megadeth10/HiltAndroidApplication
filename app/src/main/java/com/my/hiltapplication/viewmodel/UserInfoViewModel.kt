package com.my.hiltapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import com.my.hiltapplication.base.BaseNetworkViewModel
import com.my.hiltapplication.noupdate.response.Customer
import com.my.hiltapplication.noupdate.service.CustomerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by YourName on 2022/06/17.
 */
@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val customerService : CustomerService
) : BaseNetworkViewModel() {
    private val apiNameUserInfo = "api_name_userInfo"

    private val _userInfo : MutableLiveData<String> = MutableLiveData()
    val userInfo : MutableLiveData<String> = _userInfo

    private fun setUserInfo(text : String) {
        this.userInfo.postValue(text)
    }

    fun getUserInfo() {
        this.setProgress(true)
        this.setUserInfo("")
        cancelObserver(this.apiNameUserInfo)
        addObserver(
            this.apiNameUserInfo,
            this.customerService.setObserver(this.customerService.getModule().getUserInfo(),
                onSuccess = {
                    this.setProgress(false)
                    if (it is Customer) {
                        setUserInfo("nickname: ${it.nickname}, phone:${it.phone}")
                    }
                },
                onError = {
                    this.setProgress(false)
                    setUserInfo("getUserInfo false")
                }
            )
        )
    }
    override fun getLogName() = UserInfoViewModel::class.simpleName
}