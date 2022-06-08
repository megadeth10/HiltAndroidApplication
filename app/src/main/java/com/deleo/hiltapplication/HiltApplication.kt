package com.deleo.hiltapplication

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication: Application() {
    private var tag = HiltApplication::class.simpleName
    init {
        Log.e(tag, "init")
    }
}