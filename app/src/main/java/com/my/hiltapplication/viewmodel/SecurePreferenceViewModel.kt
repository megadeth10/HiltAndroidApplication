package com.my.hiltapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import com.my.hiltapplication.base.BaseViewModel
import com.my.hiltapplication.securepreference.SecureSharedPreferences
import com.my.hiltapplication.securepreference.callback.SecureStoreCallback
import com.my.hiltapplication.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurePreferenceViewModel @Inject constructor(
    private val secureSharedPreferences : SecureSharedPreferences
) : BaseViewModel() {

    private val _data : MutableLiveData<String?> = MutableLiveData(null)
    val data : MutableLiveData<String?> = _data

    private val _progressState : MutableLiveData<Boolean?> = MutableLiveData(null)
    val progressState : MutableLiveData<Boolean?> = _progressState

    private val storeKeyName = "aaa"

    private val list = arrayListOf<String>(
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1",
        "1"
    )

    fun initData() {
        secureSharedPreferences.getString(storeKeyName, "").apply {
            if (this.isNotEmpty()) {
                setData(this)
            }
        }
    }

    fun setProgressState(newState : Boolean?) {
        CoroutineScope(Dispatchers.Main).launch {
            this@SecurePreferenceViewModel._progressState.value = newState
            Log.e(tagName, "setProgressState() state: $newState")
        }
    }

    private fun setData(text : String?) {
        this._data.value = text
    }


    fun save(newText : String) {
        this.setProgressState(true)
        this.setData(null)
        val callback : SecureStoreCallback = object : SecureStoreCallback {
            override fun storeFinish() {
                this@SecurePreferenceViewModel.setProgressState(false)
            }
        }
        secureSharedPreferences.storeArrayList("aaaalist", this.list, callback)
//        secureSharedPreferences.storeString(this.storeKeyName, newText, callback)
    }

    override fun getLogName() = SecurePreferenceViewModel::class.simpleName
}