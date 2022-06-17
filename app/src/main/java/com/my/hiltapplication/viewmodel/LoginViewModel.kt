package com.my.hiltapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import com.my.hiltapplication.base.BaseNetworkViewModel
import com.my.hiltapplication.network.NetworkUtil
import com.my.hiltapplication.noupdate.request.LoginUserRequestBody
import com.my.hiltapplication.noupdate.response.Customer
import com.my.hiltapplication.noupdate.response.LoginUserResponse
import com.my.hiltapplication.noupdate.response.ResultResponse
import com.my.hiltapplication.noupdate.service.AuthService
import com.my.hiltapplication.noupdate.service.CustomerService
import com.my.hiltapplication.noupdate.variant.Name
import com.my.hiltapplication.store.TokenStore
import com.my.hiltapplication.util.AES128Util
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by YourName on 2022/06/16.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService : AuthService,
    private val tokenStore : TokenStore,
    private val customerService : CustomerService
) : BaseNetworkViewModel() {
    private val apiNameAuth = "api_name_get_auth"
    private val apiNameExpiredAuth = "api_name_expired_auth"
    private val apiNameUserInfo = "api_name_userInfo"

    private var aes128Util : AES128Util = AES128Util(Name.aes256key)
    private val _resultText : MutableLiveData<String> = MutableLiveData()
    val resultText : MutableLiveData<String> = _resultText

    private val _userInfo : MutableLiveData<String> = MutableLiveData()
    val userInfo : MutableLiveData<String> = _userInfo

    private fun setUserInfo(text : String) {
        this.userInfo.postValue(text)
    }

    private fun setResultText(text : String) {
        this._resultText.postValue(text)
    }

    fun getLoginAuth(id : String, pw : String) {
        setProgress(true)
        cancelObserver(apiNameAuth)

        val encPw = aes128Util.encode(pw)
        val request = NetworkUtil.makeRequestBody(
            LoginUserRequestBody(
                accountId = id,
                password = encPw
            )
        )
        addObserver(this.apiNameAuth,
            this.authService.setObserver(
                singleObserver = this.authService.getModule().normalLogin(request),
                onSuccess = {
                    setProgress(false)
                    if (it is LoginUserResponse) {
                        setResultText("get user auth success")
                        tokenStore.setUserToken(it.token.accessToken, it.token.refreshToken, it.token.key)
                    } else {
                        setResultText("get user auth fail")
                    }
                },
                onError = {
                    setProgress(false)
                    setResultText("get user auth fail ${it.message}")
                }
            ))
    }

    fun expiredAuth() {
        setProgress(true)
        cancelObserver(this.apiNameExpiredAuth)
        addObserver(
            this.apiNameExpiredAuth,
            this.authService.setObserver(this.authService.getModule().logout(),
                onSuccess = {
                    setProgress(false)
                    if (it is ResultResponse) {
                        setResultText("expired auth success")
                        if(it.result){
                            tokenStore.expiredToken()
                        }
                    }
                },
                onError = {
                    setProgress(false)
                    tokenStore.expiredToken()
                    setResultText("expired auth false")
                }
            )
        )
    }

    fun getUserInfo() {
        this.setUserInfo("")
        cancelObserver(this.apiNameUserInfo)
        addObserver(
            this.apiNameUserInfo,
            this.customerService.setObserver(this.customerService.getModule().getUserInfo(),
                onSuccess = {
                    if (it is Customer) {
                        setUserInfo("nickname: ${it.nickname}, phone:${it.phone}")
                    }
                },
                onError = {
                    setUserInfo("getUserInfo false")
                }
            )
        )
    }

    override fun getLogName() = LoginViewModel::class.simpleName
}