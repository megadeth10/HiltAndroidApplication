package com.my.hiltapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.my.hiltapplication.base.BaseNetworkViewModel
import com.my.hiltapplication.noupdate.response.VersionResponse
import com.my.hiltapplication.noupdate.service.VersionService
import com.my.hiltapplication.store.UserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel // 공식 inject annotation
class UserViewModel @Inject constructor(
    private val userStore : UserStore,
    private val versionService : VersionService
) : BaseNetworkViewModel() {
    private var _modelData : MutableLiveData<String?> = MutableLiveData(null)
    val modelData : MutableLiveData<String?> = _modelData

    private var _versionData : MutableLiveData<VersionResponse?> = MutableLiveData(null)
    val versionData : MutableLiveData<VersionResponse?> = _versionData

    private val apiNameVersion = "api_name_version"
    init {
        Log.e(tagName, "init() versionModule: $versionService")
    }
    fun setModelData(text : String?) = text.also { this._modelData.value = it }

    fun setUser(text : String?) = text.also {
        this.userStore.setUser(it)
    }

    fun getUser() = this.userStore.getUserStore()


    fun currentState() {
        this.userStore.currentState()
    }

    fun setVersion(data : VersionResponse?) {
        this._versionData.value = data
    }

    fun getVersion() {
        cancelObserver(apiNameVersion)
        addObserver(apiNameVersion,
            versionService.getModule().getVersion().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<VersionResponse>() {
                    override fun onSuccess(t : VersionResponse) {
                        setVersion(t)
                    }

                    override fun onError(e : Throwable) {
                        Log.e(tagName, "getVersion() error", e)
                    }

                })
        )
//        addObserver(apiNameVersion,
//            versionModule.getModule(
//                onSuccess = {
//                    if (it is VersionResponse) {
//                        Log.e(tagName, "getVersion() result: $it")
//                    }
//                },
//                onError = {
//                    Log.e(tagName, "getVersion() error", it)
//                }
//            )
//        )
    }

    override fun getLogName() = UserViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        Log.e(tagName, "onCleared()")
    }
}