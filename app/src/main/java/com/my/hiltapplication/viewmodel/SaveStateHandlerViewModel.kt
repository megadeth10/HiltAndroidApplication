package com.my.hiltapplication.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.my.hiltapplication.base.BaseViewModel
import com.my.hiltapplication.scene.SaveStateHandlerActivity
import com.my.hiltapplication.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by YourName on 2022/07/07.
 */

class SaveStateHandlerViewModel(
    savedStateHandle : SavedStateHandle
) : BaseViewModel() {
    override fun getLogName() = SaveStateHandlerViewModel::class.simpleName
    private val name = savedStateHandle.get<String>(SaveStateHandlerActivity.ParamName)

    init {
        Log.e(tagName, "init() name: $name")
    }
}