package com.my.hiltapplication.store

import com.my.hiltapplication.base.BaseStore
import com.my.hiltapplication.noupdate.variant.Name
import com.my.hiltapplication.securepreference.SecureSharedPreferences
import com.my.hiltapplication.securepreference.SecureStorageKeyMap
import com.my.hiltapplication.util.AES128Util
import com.my.hiltapplication.util.Log
import java.util.*
import javax.inject.Inject

class TokenStore @Inject constructor(
    private val userStore : UserStore,
    private val secureSharedPreferences : SecureSharedPreferences
) : BaseStore() {
    private var token : String? = null
    private val appCheckerKeyLength = 16

    // 회원 전용 appChecker key
    private var key : String? = null

    // 비회원 전용 appChecker key
    private var basicKey : String

    init {
        this.getTagName(this)
        Log.e(tag, "TokenStore init() this:$this")
        this.token = secureSharedPreferences.getString(SecureStorageKeyMap.USER_TOKEN, Name.token)
        val basicToken = this.token
        this.basicKey = basicToken!!.substring(basicToken.length - this.appCheckerKeyLength, basicToken.length)
    }

    fun getToken() = this.token

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