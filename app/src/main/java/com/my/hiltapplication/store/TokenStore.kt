package com.my.hiltapplication.store

import com.my.hiltapplication.base.BaseStore
import com.my.hiltapplication.enum.StateStore
import com.my.hiltapplication.network.Network
import com.my.hiltapplication.network.NetworkUtil
import com.my.hiltapplication.noupdate.header.HeaderInterceptor
import com.my.hiltapplication.noupdate.request.TokenRequestBody
import com.my.hiltapplication.noupdate.response.ResultResponse
import com.my.hiltapplication.noupdate.response.TokenResponse
import com.my.hiltapplication.noupdate.service.AuthService
import com.my.hiltapplication.noupdate.variant.Name
import com.my.hiltapplication.securepreference.SecureSharedPreferences
import com.my.hiltapplication.securepreference.SecureStorageKeyMap
import com.my.hiltapplication.util.AES128Util
import com.my.hiltapplication.util.ConstValue
import com.my.hiltapplication.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.math.abs

// 상태 변경 알림을 콜백을 추가해야함.
class TokenStore @Inject constructor(
    private val userStore : UserStore,
    private val secureSharedPreferences : SecureSharedPreferences
) : BaseStore() {
    private val tokenExpiredTimeInMillisecond = 1 * ConstValue.HOUR_IN_MILLISECOND
    private val repeatTimeInMillisecond = 20L * ConstValue.MINUTE_IN_MILLISECOND
    private var tokenRefreshObserver : DisposableSingleObserver<*>? = null
    private var tokenTimeUpdateObserver : Disposable? = null

    private var token : String? = null
    private var refreshToken : String? = null
    private val appCheckerKeyLength = 16
    private var tokenUpdateStartTime : Long? = null

    // 회원 전용 appChecker key
    private var key : String? = null
    private var keyDecodeUtil : AES128Util? = null

    // 비회원 전용 appChecker key
    private var basicKey : String = ""
    private var authService : AuthService? = null

    // Todo StateStore enum class로 호출하려니 오류가 발생해서 일단 Int로
    //  error: as of release 5, 'enum' is a keyword, and may not be used as an identifier
    private val stateCallbackMap: HashMap<String, ((Int) -> Unit)> = HashMap()
    init {
        this.getTagName(this)
        Log.e(tag, "TokenStore init() this:$this")
        this.keyDecodeUtil = AES128Util(Name.encodeKey)
        this.basicKey = Name.token.substring(Name.token.length - this.appCheckerKeyLength, Name.token.length)
        val storeUserToken = secureSharedPreferences.getString(SecureStorageKeyMap.USER_TOKEN, Name.token)
        val storeRefreshToken = secureSharedPreferences.getString(SecureStorageKeyMap.REFRESH_TOKEN, Name.token)
        var key : String? = secureSharedPreferences.getString(SecureStorageKeyMap.USER_KEY, "")
        if (key?.isEmpty() == true) {
            key = null
        }
        this.tokenUpdateStartTime = secureSharedPreferences.getLong(SecureStorageKeyMap.APP_PAUSE_TIME, 0)
        this.setInit(storeUserToken, storeRefreshToken, key)
        this.storeState = StateStore.Init
        // 이부분이 상호 참조 문제로 어쩔수 없이 내부 객체로 ??
        // authService = AuthService(SingletonModule().getNetwork(this))
        authService = AuthService(Network(HeaderInterceptor(this)))
    }

    /**
     * 사용자 토큰인이 확인
     */
    fun haveUserAuth() = this.token != Name.token

    private fun createRepeatTimerObserver(pastTime: Long) {
        this.closeTimerObserver()
        var initialDelay = abs(repeatTimeInMillisecond - pastTime)
        if (pastTime >= repeatTimeInMillisecond) {
            initialDelay = 0L
        }
        Log.e(
            "TokenRefreshManager",
            "createRepeater() initialDelay(/1000): ${initialDelay / 1000}"
        )
        this.tokenTimeUpdateObserver =
            Observable.interval(
                initialDelay,
                repeatTimeInMillisecond,
                TimeUnit.MILLISECONDS
            )
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe {
                    tokenValidTimeUpdate()
                }
    }

    private fun closeTimerObserver() {
        this.tokenTimeUpdateObserver?.dispose()
    }

    /**
     * Activity onResume()에서만 호출 하자.
     */
    fun checkValidationToken(callName:String, callFunction : (Int) -> Unit) {
        this.stateCallbackMap[callName] = callFunction
        if (this.haveUserAuth()) {
            val pauseTime = this.tokenUpdateStartTime ?: 0
            val currentTime = Calendar.getInstance().timeInMillis
            val appPauseTime = abs(currentTime - (pauseTime))

            if (appPauseTime >= this.tokenExpiredTimeInMillisecond || this.storeState != StateStore.Run) {
                // refreshToken
                this.getNewToken()
            } else if (appPauseTime >= this.repeatTimeInMillisecond) {
                // token update
                this.tokenValidTimeUpdate()
            } else {
                this.createRepeatTimerObserver(appPauseTime)
                this.callStateUpdate(StateStore.Run)
            }
        } else {
            // Activity 마다 체크 할때 기본 Token이면 동작을 정지 한다.
            this.closeTimerObserver()
            this.tokenRefreshEnd(false)
        }
    }

    private fun getNewToken() {
        tokenRefreshObserver?.dispose()
        if (this.authService != null && this.refreshToken != null) {
            val request = NetworkUtil.makeRequestBody(
                TokenRequestBody(
                    this.refreshToken!!
                )
            )
            tokenRefreshObserver = this.authService!!.getModule().newToken(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableSingleObserver<TokenResponse>() {
                    override fun onSuccess(t : TokenResponse) {
                        Log.e(tag, "getNewToken() success")
                        this@TokenStore.setUserToken(
                            token = t.accessToken,
                            refreshToken = t.refreshToken,
                            key = t.key
                        )
                        this@TokenStore.tokenRefreshEnd(true)
                    }

                    override fun onError(e : Throwable) {
                        Log.e(tag, "getNewToken() fail")
//                        this@TokenStore.tokenRefreshEnd(false)
                    }
                })
        } else {
            // 앱 재시작????
            // 비회원 동작?
            this.tokenRefreshEnd(false)
        }
    }

    /**
     * 토큰 시간 연장
     */
    private fun tokenValidTimeUpdate() {
        tokenRefreshObserver?.dispose()
        authService?.let {
            tokenRefreshObserver = it.getModule().refreshToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableSingleObserver<ResultResponse>() {
                    override fun onSuccess(t : ResultResponse) {
                        if (t.result) {
                            Log.e(tag, "tokenValidTimeUpdate() success")
                            this@TokenStore.tokenRefreshEnd(true)
                        } else {
                            Log.e(tag, "tokenValidTimeUpdate() fail")
                            this@TokenStore.tokenRefreshEnd(false)
                        }
                    }

                    override fun onError(e : Throwable) {
                        Log.e(tag, "tokenValidTimeUpdate() fail")
//                        this@TokenStore.tokenRefreshEnd(false)
                    }
                })
        }
    }

    /**
     * 토큰 체크 결과 확익 알림
     */
    private fun callStateUpdate(state : StateStore) {
        CoroutineScope(Dispatchers.Main).launch {
            this@TokenStore.stateCallbackMap.forEach {
                it.value(state.ordinal)
            }
            this@TokenStore.stateCallbackMap.clear()
        }
    }

    /**
     * 토큰 갱신이 로직의 종료 설정
     * @param isUserAuth : T user Auth F: basic Auth
     */
    private fun tokenRefreshEnd(isUserAuth : Boolean) {
        if (isUserAuth) {
            this.setUserAuthAndRun()
        } else {
            this.setBasicAuthAndRun()
        }
    }

    /**
     * 오류나 유저 토큰이 없을때 기본 토큰으로 동작 시작됨을 설정
     */
    private fun setBasicAuthAndRun() {
        this.setInit(Name.token, null, null)
        this.setState(StateStore.Run)
        this.callStateUpdate(StateStore.Run)
    }

    /**
     * 유저 토큰으로 갱신 또는 시간 업데이트 완료 이후 동작 시작됨을 설정
     */
    private fun setUserAuthAndRun() {
        this.tokenUpdateTime()
        this.setState(StateStore.Run)
        this.callStateUpdate(StateStore.Run)
    }

    private fun setInit(token : String, refreshToken : String?, key : String?) {
        this.token = token
        this.refreshToken = refreshToken
        this.key = key
    }

    fun getToken() = this.token

    fun setUserToken(token : String, refreshToken : String, key : String) {
        this.keyDecodeUtil?.let {
            val decodeKey = it.decode(key)
            this.setInit(token, refreshToken, decodeKey)
            this.storeToken(token, refreshToken, decodeKey)
        }
    }

    fun expiredToken() {
        this.setInit(Name.token, null, null)
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

    // 토큰 시작 시작 갱신
    private fun tokenUpdateTime() {
        CoroutineScope(Dispatchers.IO).launch {
            this@TokenStore.tokenUpdateStartTime = Calendar.getInstance().timeInMillis
            this@TokenStore.createRepeatTimerObserver(0)
            Log.e(tag, "tokenUpdateTime() time : ${this@TokenStore.tokenUpdateStartTime}")
            this@TokenStore.tokenUpdateStartTime?.let {
                secureSharedPreferences.storeLong(SecureStorageKeyMap.APP_PAUSE_TIME, it, null)
            }
        }
    }

    /**
     * 스토어 상태 변경
     */
    private fun setState(newState : StateStore) {
        this.storeState = newState
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