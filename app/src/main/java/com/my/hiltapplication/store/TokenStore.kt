package com.my.hiltapplication.store

import com.my.hiltapplication.base.BaseStore
import com.my.hiltapplication.noupdate.variant.Name
import com.my.hiltapplication.securepreference.SecureSharedPreferences
import com.my.hiltapplication.securepreference.SecureStorageKeyMap
import com.my.hiltapplication.util.AES128Util
import com.my.hiltapplication.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TokenStore @Inject constructor(
    private val userStore : UserStore,
    private val secureSharedPreferences : SecureSharedPreferences
) : BaseStore() {
    private var token : String? = null
    private var refreshToken: String? = null
    private val appCheckerKeyLength = 16

    // 회원 전용 appChecker key
    private var key : String? = null
    private var keyDecodeUtil: AES128Util? = null

    // 비회원 전용 appChecker key
    private var basicKey : String = ""

    init {
        this.getTagName(this)
        Log.e(tag, "TokenStore init() this:$this")
        this.keyDecodeUtil = AES128Util(Name.encodeKey)
        this.basicKey = Name.token.substring(Name.token.length - this.appCheckerKeyLength, Name.token.length)
        val storeToken = secureSharedPreferences.getString(SecureStorageKeyMap.USER_TOKEN, Name.token)
        val storeRefreshToken = secureSharedPreferences.getString(SecureStorageKeyMap.REFRESH_TOKEN, Name.token)
        var key : String? = secureSharedPreferences.getString(SecureStorageKeyMap.USER_KEY, "")
        if (key?.isEmpty() == true) {
            key = null
        }
        this.setInit(storeToken, storeRefreshToken, key)
    }

    private fun setInit(token : String, refreshToken:String?, key : String? = null) {
        this.refreshToken = refreshToken
        this.token = token
        this.key = key
    }

    fun getToken() = this.token

    fun setUserToken(token : String, refreshToken : String, key : String) {
        val decodeKey = this.keyDecodeUtil?.decode(key)
        this.setInit(token, refreshToken, decodeKey)
        this.storeToken(token, refreshToken, key)
    }

    fun expiredToken() {
        this.setInit(Name.token, null)
        CoroutineScope(Dispatchers.IO).launch {
            secureSharedPreferences.remove(SecureStorageKeyMap.USER_TOKEN)
            secureSharedPreferences.remove(SecureStorageKeyMap.REFRESH_TOKEN)
            secureSharedPreferences.remove(SecureStorageKeyMap.USER_KEY)
        }
    }

    private fun storeToken(token : String, refreshToken : String, key : String) {
        CoroutineScope(Dispatchers.IO).launch {
            secureSharedPreferences.storeString(SecureStorageKeyMap.USER_TOKEN, token, null)
            secureSharedPreferences.storeString(SecureStorageKeyMap.REFRESH_TOKEN, refreshToken, null)
            secureSharedPreferences.storeString(SecureStorageKeyMap.USER_KEY, key, null)
        }
    }

    /**
     * 암호화된 시간값 전달문
     */
    fun getEncodeTime() : String {
        val key : String = this.key ?: this.basicKey
        val timeInMillisecond = Calendar.getInstance().timeInMillis.toString()
        val timeEncodeUtil = AES128Util(key)
        val encodeTime = timeEncodeUtil.encode(timeInMillisecond)
        Log.e("TokenStore", "getEncodeTime() key: $key || encodeTime : $encodeTime")
        return encodeTime
    }
}