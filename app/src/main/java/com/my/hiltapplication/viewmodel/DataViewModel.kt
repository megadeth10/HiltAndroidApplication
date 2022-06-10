package com.my.hiltapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.my.hiltapplication.base.BaseNetworkViewModel
import com.my.hiltapplication.noupdate.service.VersionService
import com.my.hiltapplication.store.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel // 공식 inject annotation
class DataViewModel @Inject constructor(
    private val dataStore : DataStore,
    private val versionService : VersionService
) : BaseNetworkViewModel() {
    private var _modelData : MutableLiveData<String?> = MutableLiveData(null)
    val modelData : MutableLiveData<String?> = _modelData

    init {
        Log.e(tagName, "init() versionModule: $versionService")
    }
    fun setModelData(text : String?) = text.also { this._modelData.value = it }

    fun setData(text : String?) = text.also {
        this.dataStore.setData(it)
    }

    fun getData() = this.dataStore.getData()
    fun currentState() {
        this.dataStore.currentState()
    }

    override fun getLogName() = DataViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        Log.e(tagName, "onCleared()")
    }
}