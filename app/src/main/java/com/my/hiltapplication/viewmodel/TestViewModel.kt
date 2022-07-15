package com.my.hiltapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.my.hiltapplication.util.Log

/**
 * Created by YourName on 2022/07/15.
 */
class TestViewModel(application : Application): AndroidViewModel(application) {
    init {
        Log.e("TestViewModel", "init() application: $application")
    }
}