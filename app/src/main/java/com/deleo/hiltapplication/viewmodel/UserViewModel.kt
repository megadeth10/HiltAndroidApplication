package com.deleo.hiltapplication.viewmodel

import android.util.Log
import android.util.MutableDouble
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deleo.hiltapplication.base.BaseViewModel
import com.deleo.hiltapplication.store.UserStore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

// single instance
//@Module
//@InstallIn(SingletonComponent::class)

@HiltViewModel // 공식 inject annotation
class UserViewModel @Inject constructor(private var userStore : UserStore) : BaseViewModel() {
    private var _modelData : MutableLiveData<String?> = MutableLiveData(null)
    val modelData : MutableLiveData<String?> = _modelData

    fun setModelData(text : String?) = text.also { this._modelData.value = it }

    fun setUser(text : String?) = text.also {
        this.userStore.setUser(it)
    }

    fun getUser() = this.userStore.getUserStore()
    fun currentState() {
        this.userStore.currentState()
    }

    override fun getLogName() = UserViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        Log.e(tagName, "onCleared()")
    }
}